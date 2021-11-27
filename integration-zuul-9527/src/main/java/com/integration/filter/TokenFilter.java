package com.integration.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.integration.config.Audience;
import com.integration.config.IomPlatformParam;
import com.integration.config.ModuleEnum;
import com.integration.utils.DataUtils;
import com.integration.utils.SerialNumberUtil;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TokenFilter implements GlobalFilter, Ordered {

    Logger log = LoggerFactory.getLogger(TokenFilter.class);

    private static final String routepre = "/hlkj/";
    private static final String routeend = "/**";

    //需要被鉴权访问的模块
    private static final List<String> validateRouteList = Arrays.asList("dcv", "dmv",
            "alarm", "pmv", "smv", "robot", "ocp");

    @Resource
    private TokenVerification tokenVerification;

    @Autowired
    private Audience audience;

    private boolean check = IomPlatformParam.LICENSES_ENABLE;

    String[] includeUrls = new String[]{
            "/hlkj/camp/login",
            "/hlkj/camp/sgcc/loginByUserInfo",
            "/hlkj/camp/loginByAesCode",
            "/hlkj/camp/licenseStatus",
            "/hlkj/interfacecamp/login",
            "/hlkj/camp/VerificationTicket",
            "/hlkj/camp/VerificationTicketforBody",
            "/hlkj/camp/VerificationTicketforJson",
            "/hlkj/his/findGj",
            "/hlkj/his/pinlva",
            "/hlkj/his/oldLoad",
            "/hlkj/his/guimo",
            "/hlkj/his/readDOC",
            "/hlkj/his/findYGPL",
            "/hlkj/camp/faceId",
            "/hlkj/jniom/",
            "/websocket/info",
            "/hlkj/cmdb/ciInfoToInterface",
            "/hlkj/alarm/process/initProcessToEs",
            "/hlkj/alarm/security/saGetProbeLine",
            "/hlkj/cmdb/typeItem/getSecurityCiInfo",
            "/hlkj/pmv/dcvBigScreen/louShuiXian3D",
            "/hlkj/pmv/dcvBigScreen/wenShiDu3D",
            "/hlkj/dmv/diagramInfo/queryDiagramInfoByCiCode",
            "/hlkj/dmv/getDiagramInfoByDiagramId",
            "/hlkj/alarm/currentinfo/closeEventByCIAndKpi",
            "/hlkj/alarm/currentinfo/getEventByCIAndKpi",
            "/hlkj/alarm/currentinfo/confirmEventByCIAndKpi",
            "/hlkj/alarm/currentinfo/cancelConfirmEventByCIAndKpi",
            "/hlkj/camp/openApi/**",
            "/hlkj/alarm/process/initEventInfoToEs"
    };

    @Override
    public int getOrder() {
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().getPath();
        //通过uri判断是否有模块权限
        boolean hasModule = hasModule(uri);

        log.info(String.format("模块验证 %s", hasModule));
        if (check && !hasModule) {
            //获取访问模块名称
            String moduleName = getModuleNameByUri(uri);
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            response.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
            return response.writeWith(Flux.just(response.bufferFactory().wrap(JSON.toJSONString(DataUtils.returnPrExt("422", String.format("%s模块未授权，请申请授权！", moduleName), null, 0)).getBytes(StandardCharsets.UTF_8))));
        }


        boolean needFilter = isNeedFilter(uri);

        //websocket请求
        if (isWebsocketRequest(exchange)) {
            return chain.filter(exchange);
        }
        if (!needFilter) {
            return chain.filter(exchange);
        } else if (audience.getDfilter() == 1) {
            return chain.filter(exchange);
        } else {

            List<String> headers = Optional.ofNullable(request.getHeaders().get("token")).orElse(
                    Collections.EMPTY_LIST
            );
            String username = headers.size() > 0 ? headers.get(0).trim() : "";

            boolean bool = tokenVerification.TokenVerification(username);

            if (bool) {
                return chain.filter(exchange);
            } else {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                if (TokenUtils.isKickOut(username)) {
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return response.writeWith(Flux.just(response.bufferFactory().wrap(JSON.toJSONString(DataUtils.returnPrExt("403", "该账户在其他设备登录，请重新登录", null, 0)).getBytes(StandardCharsets.UTF_8))));
                }
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return response.writeWith(Flux.just(response.bufferFactory().wrap(JSON.toJSONString(DataUtils.returnPrExt("403", "登录超时，请重新登录", null, 0)).getBytes(StandardCharsets.UTF_8))));
            }

        }
    }

    private boolean isWebsocketRequest(ServerWebExchange exchange) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route.getUri().getSchemeSpecificPart().startsWith("ws:") ||
                route.getUri().getSchemeSpecificPart().startsWith("wss:")) {
            return true;
        }
        return false;
    }

    /**
     * 检测是否可以访问链接
     *
     * @param uri
     * @return true 可以访问
     */
    private boolean hasModule(String uri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean needCheck = false;
        for (String item : validateRouteList) {
            //访问连接在授权范围
            if (antPathMatcher.match(String.join("", routepre, item, routeend), uri)) {
                needCheck = true;
                break;
            }
        }
        if (!needCheck) {
            return true;
        }

        List<String> moduleList = SerialNumberUtil.allowModule();
        log.info(String.format("授权模块： %s", JSONObject.toJSONString(moduleList)));
        for (String item : moduleList) {
            //访问连接在授权范围
            log.info(String.format("uri:%s, 模块：%s, ，结果：%s", uri, item, String.join("", routepre, item, routeend)));
            if (antPathMatcher.match(String.join("", routepre, item, routeend), uri)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNeedFilter(String uri) {

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String includeUrl : includeUrls) {
            if (antPathMatcher.match(includeUrl, uri)) {
                return false;
            }
        }

        return true;
    }


    public static String getModuleNameByUri(String uri) {
        if (uri == null) {
            return "";
        }
        if (StringUtils.isEmpty(uri)) {
            return "";
        }
        String[] arr = uri.split("/");
        if (arr.length > 2) {
            String name = arr[2];
            ModuleEnum.module module = ModuleEnum.module.valueOf(name);
            if (module != null) {
                return module.getName();
            }
        }
        return "";
    }


    public static void main(String[] args) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        System.out.println(antPathMatcher.match("/alert/**", "/alert/a"));
        System.out.println(antPathMatcher.match("/alert/**", "/alert/a/b"));
        System.out.println(antPathMatcher.match("/alert/**", "/alert/a/b/c"));
        System.out.println(antPathMatcher.match("/alert/**", "/alert1"));

        String item = "dmv";
        String uri = "/hlkj/dmv/worldMap/queryWorldMapCiAlarmInfo";
        System.out.println(antPathMatcher.match(String.join("", routepre, item, routeend), uri));

        System.out.println(getModuleNameByUri("/hlkj/camp/login"));
    }
}
