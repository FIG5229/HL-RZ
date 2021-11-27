package com.integration.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.aop.log.service.IomCampActionApiService;
import com.integration.config.IomPlatformParam;
import com.integration.entity.PageResult;
import com.integration.service.LisenceService;
import com.integration.utils.DataUtils;
import com.integration.utils.SerialNumberUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PostFilter implements GlobalFilter, Ordered {

	Logger log = LoggerFactory.getLogger(PostFilter.class);

	private static final String updateCheckNumUrl = "/hlkj/camp/registerAuth";


	private static final String websocketUrlPreFix = "/websocket/";


	@Autowired
	private IomCampActionApiService actionApiService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private LisenceService lisenceService;

	private boolean check = IomPlatformParam.LICENSES_ENABLE;

	static List<String> includeUrls = new ArrayList<>();

	static List<String> packageExcludeUrls = new ArrayList<>();


	static {
		includeUrls.add(updateCheckNumUrl);
		includeUrls.addAll(LicenseFilter.includeUrls);
		includeUrls = Collections.unmodifiableList(includeUrls);
		packageExcludeUrls.add("/hlkj/cmdb/typeItem/exportExcelItem");
		packageExcludeUrls.add("/hlkj/cmdb/exportDataRelToExcelAll");
		packageExcludeUrls.add("/hlkj/cmdb/exportDataRelToExcel");
		packageExcludeUrls.add("/hlkj/cmdb/downloadTemplet");
		packageExcludeUrls.add("/hlkj/cmdb/exportExcelData");
		packageExcludeUrls.add("/hlkj/cmdb/exportIcon");
		packageExcludeUrls.add("/hlkj/cmdb/exportKpiFY");
		packageExcludeUrls.add("/hlkj/smv/manualInspection/downloadServerFileByRoute");
		packageExcludeUrls.add("/hlkj/dmv/diagramInfo/exportView");
		packageExcludeUrls.add("/hlkj/alarm/currentinfo/downloadTemplet");
		packageExcludeUrls.add("/hlkj/cmdb/rltRule/exportConfigureReports");
		packageExcludeUrls.add("/hlkj/pmv/bigScreen/exportSimulationPerformance");
		packageExcludeUrls.add("/hlkj/alarm/currentinfo/exportEvent");
		packageExcludeUrls.add("/hlkj/interface/zabbix/excelAnalysisGetItem");
		packageExcludeUrls.add("/hlkj/camp/bigScreen/exportSimulationPerformance");
		packageExcludeUrls.add("/hlkj/smv/scheduleMain/export");
		packageExcludeUrls.add("/hlkj/ocp/iomOcpFunctionCmd/downLoadCmdTemplate");
		packageExcludeUrls.add("/hlkj/cmdb/exportExcelDataDeploy");
		packageExcludeUrls.add("/hlkj/interface/dataReplace/exportItem");
	}


	@Override
	public int getOrder() {
		// -1 is response write filter, must be called before that
		return -2;
	}

	private boolean isWebSocketRequest(ServerWebExchange exchange){
		if(exchange.getRequest().getURI().getPath().contains(websocketUrlPreFix)){
			return true;
		}
		return false;
	}

	private boolean isNeedPackage(ServerWebExchange exchange){
		String uri = exchange.getRequest().getURI().getPath();
		long count = packageExcludeUrls.stream().filter(item->{
			return item.equalsIgnoreCase(uri);
		}).count();
		if(count > 0){
			return false;
		}
		return true;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if(isWebSocketRequest(exchange)){
			return chain.filter(exchange);
		}

		if(!isNeedPackage(exchange)){
			ServerHttpResponse response = exchange.getResponse();
			ServerHttpRequest request = exchange.getRequest();
			DataBufferFactory bufferFactory = response.bufferFactory();
			String requestBodyStr = "";
			ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
				@Override
				public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
					try {
						if (body instanceof Flux) {
							Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

							return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
								DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
								DataBuffer join = dataBufferFactory.join(dataBuffers);
								// probably should reuse buffers
								byte[] content = new byte[join.readableByteCount()];
								join.read(content);
								//释放掉内存
								DataBufferUtils.release(join);
								String s = new String(content, Charset.forName("UTF-8"));

								String logId = actionlog(request, response, s, requestBodyStr);
								if(StringUtils.isNotEmpty(logId)){
									PageResult pg = DataUtils.returnPr(false, "程序错误，请联系管理员！");
									pg.setReturnCode(logId);

									byte[] uppedContent = JSONObject.toJSONString(pg).getBytes(StandardCharsets.UTF_8);
									response.getHeaders().setContentLength(uppedContent.length);
									return bufferFactory.wrap(uppedContent);
								}

								return bufferFactory.wrap(content);
							}));
						}
						return super.writeWith(body);
					}catch (Exception e){
						e.printStackTrace();
						return super.writeWith(body);
					}
				}
			};

			return chain.filter(exchange.mutate().response(decoratedResponse).build());
		}



		try {
			HttpHeaders headers = exchange.getResponse().getHeaders();
            ServerHttpResponse response = exchange.getResponse();
			ServerHttpRequest request = exchange.getRequest();
			DataBufferFactory bufferFactory = response.bufferFactory();
			HttpMethod method = request.getMethod();
			String requestBodyStr = "";
			//post请求获取内容只有一次，会出问题
//			HttpMethod.POST.equals(method) ? resolveBodyFromRequest(request) : JSONObject.toJSONString(request.getQueryParams());




			ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
				@Override
				public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
					try {
						if (body instanceof Flux) {
							updateCheckStatus(exchange);

							Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

							if(response.getHeaders().getContentLength() == 0){
								String s = PageResult.getPageResultString(null, getHeader(headers,"returnMessage"),
										getHeader(headers,"returnBoolean"),false);
								byte[] uppedContent = s.getBytes(StandardCharsets.UTF_8);
								response.getHeaders().setContentLength(uppedContent.length);
								return response.writeWith(Flux.just(bufferFactory.wrap(uppedContent)));
							}

							return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
								boolean isString = false;
								String contentType = getHeader(headers,"Content-Type");
								//如果返回值是个字符串
								if (StringUtils.isNotEmpty(contentType)&&contentType.contains("text")) {
									isString = true;
								}
								DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
								DataBuffer join = dataBufferFactory.join(dataBuffers);
								// probably should reuse buffers
								byte[] content = new byte[join.readableByteCount()];
								join.read(content);
								//释放掉内存
								DataBufferUtils.release(join);
								String s = new String(content, Charset.forName("UTF-8"));


//								String url = request.getPath().toString();
//								Object params = request.getQueryParams();

//								log.info("请求长度：" + StringUtils.length(requestBodyStr) +
//										"，返回data长度:" + StringUtils.length(s));
//								log.info("请求地址:【{}】请求参数:GET【{}】|POST:【\n{}\n】,响应数据:【\n{}\n】", url,
//										JSONObject.toJSONString(params), requestBodyStr, s);


								String actionLogId = actionlog(request, response, s, requestBodyStr);

								String returnMessage = getHeader(headers,"returnMessage");
								String returnBoolean = getHeader(headers,"returnBoolean");
								if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)){
									returnMessage = "程序错误，联系管理员！";
									returnBoolean = "false";
									s = JSONObject.toJSONString(DataUtils.returnPr(returnBoolean,returnMessage));
									PageResult pr = JSONObject.parseObject(s, PageResult.class);
									pr.setReturnCode(actionLogId);

									s = JSONObject.toJSONString(pr);
								}else if(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
									returnMessage = "程序错误，联系管理员！";
									returnBoolean = "false";
									s = JSONObject.toJSONString(DataUtils.returnPr(returnBoolean,returnMessage));
									PageResult pr = JSONObject.parseObject(s, PageResult.class);
									pr.setReturnCode(actionLogId);

									s = JSONObject.toJSONString(pr);
								}else{
									s = PageResult.getPageResultString(s, returnMessage,
											returnBoolean,isString);
								}
								byte[] uppedContent = s.getBytes(StandardCharsets.UTF_8);
								response.getHeaders().setContentLength(uppedContent.length);
								return bufferFactory.wrap(uppedContent);
							}));
						}
						updateCheckStatus(exchange);
						return super.writeWith(body);
					}catch (Exception e){
						e.printStackTrace();
						return super.writeWith(body);
					}
				}
			};

			return chain.filter(exchange.mutate().response(decoratedResponse).build());
//			return chain.filter(exchange);
		} catch (Exception e) {
		}
		return null;
	}


	private String actionlog(ServerHttpRequest request, ServerHttpResponse response, String s, String requestBodyStr){
		try{
			int status = response.getStatusCode().value();
			boolean logFlag = false;
			if(status != 200){
				logFlag = true;
			}
			if(!logFlag){
				try{
					if(s.contains("returnBoolean")){
						PageResult rs = JSONObject.parseObject(s, PageResult.class);
						logFlag = !rs.isReturnBoolean();
					}
				}catch (Exception e){
					log.debug("类型不是PageResult");
				}
			}
			if(logFlag){
				String token = getHeader(request.getHeaders(), "token");
				String userId = null;
				String userDlzh = null;
				String userName = null;
				if(!StringUtils.isEmpty(token)){
					userId = TokenUtils.getTokenUserId(token);
					userDlzh = TokenUtils.getTokenDldm(token);
					if(userId.equals("-1")){
						userName = "system";
					} else {
						userName = TokenUtils.getTokenUserName(token);
					}
				}

				return actionApiService.saveAction(request.getURI().getPath(), requestBodyStr,  s, userId, userDlzh, userName);

			}
		}catch (Exception e){
			log.error("记录请求信息出错！");
			log.debug("记录请求信息出错！{}",e);
		}
		return null;
	}


	private String getHeader(HttpHeaders headers,String name) {
		if (headers!=null&&headers.size()>0&&StringUtils.isNotEmpty(name)) {
            List<String> headerValue = Optional.ofNullable(headers.get(name)).orElse(
                    Collections.EMPTY_LIST
            );
            return headerValue.size() > 0 ? headerValue.get(0).trim() : "";
		}
		return null;
	}

	private void updateCheckStatus(ServerWebExchange exchange){
        ServerHttpRequest request = exchange.getRequest();
		if(request.getURI().getPath().equals(updateCheckNumUrl)){
			String url = request.getURI().getPath();
			//Long num = includeUrls.stream().filter(s -> url.startsWith(s)).count();
			if(check){
				//需要重新检查是否注册
				lisenceService.authCodeCheck();
				boolean useable = SerialNumberUtil.isUseable();
				if(!useable){
					int authStatus = SerialNumberUtil.authFlag();
					// 返回错误码
					exchange.getResponse().setStatusCode(HttpStatus.valueOf(authStatus));
				}
			}
		}
	}

	/**
	 * 从Flux<DataBuffer>中获取字符串的方法
	 *
	 * @return 请求体
	 */
	private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
		//获取请求体
		Flux<DataBuffer> body = serverHttpRequest.getBody();

		AtomicReference<String> bodyRef = new AtomicReference<>();
		body.subscribe(buffer -> {
			CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
			DataBufferUtils.release(buffer);
			bodyRef.set(charBuffer.toString());
		});
		//获取request body
		return bodyRef.get();
	}

}
