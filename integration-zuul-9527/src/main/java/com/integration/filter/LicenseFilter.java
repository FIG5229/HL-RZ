package com.integration.filter;

import com.alibaba.fastjson.JSON;
import com.integration.config.IomPlatformParam;
import com.integration.utils.DataUtils;
import com.integration.utils.SerialNumberUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@ConditionalOnProperty(name = "product.license")
@Component
public class LicenseFilter implements GlobalFilter, Ordered {



	/**
	 * url列表 授权白名单
	 */
	public static List<String> includeUrls = new ArrayList<>();

	static {
		includeUrls.add("/hlkj/camp/authClientCode");
		includeUrls.add("/hlkj/camp/registerAuth");
		includeUrls.add("/hlkj/camp/delAuthServer");
		includeUrls.add("/hlkj/camp/authinfo");
		includeUrls.add("/hlkj/camp/login");
		includeUrls.add("/hlkj/camp/loginByAesCode");
		includeUrls.add("/hlkj/camp/sgcc/loginByUserInfo");
		includeUrls.add("/hlkj/camp/findMenuListByCzryRole");
		includeUrls.add("/hlkj/camp/logout");
		includeUrls = Collections.unmodifiableList(includeUrls);
	};

	/**
	 * 从token拿过来的白名单 本部分数据授权会允许路由，但是会改变其 response.status
	 */
	public static List<String> allowUrls = new ArrayList<>();
	static {
		allowUrls.add("/hlkj/interfacecamp/login");
		allowUrls.add("/hlkj/camp/VerificationTicket");
		allowUrls.add("/hlkj/camp/VerificationTicketforBody");
		allowUrls.add("/hlkj/camp/VerificationTicketforJson");
		allowUrls.add("/hlkj/his/findGj");
		allowUrls.add("/hlkj/his/pinlva");
		allowUrls.add("/hlkj/his/oldLoad");
		allowUrls.add("/hlkj/his/guimo");
		allowUrls.add("/hlkj/his/readDOC");
		allowUrls.add("/hlkj/his/findYGPL");
		allowUrls.add("/hlkj/camp/faceId");
		allowUrls.add("/hlkj/jniom/");
		allowUrls = Collections.unmodifiableList(allowUrls);
	};

	@Override
	public int getOrder() {
		return -2;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if(!IomPlatformParam.LICENSES_ENABLE){
			return chain.filter(exchange);
		}
		ServerHttpRequest request = exchange.getRequest();
		String uri = request.getURI().getPath();
		boolean needFilter = isNeedFilter(uri);


		//查看服务器是否授权

		boolean useable = SerialNumberUtil.isUseable();

		if (needFilter) {
			if(!useable){
				int authStatus = SerialNumberUtil.authFlag();

				if(allowVisit(uri)){

					return chain.filter(exchange);
				}else{

					ServerHttpResponse response = exchange.getResponse();

					// 返回错误码
					response.setStatusCode(HttpStatus.valueOf(authStatus));
					// 返回错误内容
					return response.writeWith(Flux.just(response.bufferFactory().wrap(JSON.toJSONString(DataUtils.returnPrExt(String.valueOf(authStatus), "", null, 0)).getBytes())));
				}

			}


		}

		return chain.filter(exchange);
	}


	public boolean isNeedFilter(String uri) {

		for (String includeUrl : includeUrls) {
			if (uri.startsWith(includeUrl)) {
				return false;
			}
		}

		return true;
	}

	public boolean allowVisit(String uri) {

		for (String includeUrl : allowUrls) {
			if (uri.startsWith(includeUrl)) {
				return true;
			}
		}

		return false;
	}

}
