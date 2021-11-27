package com.integration.utils.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
@Component
@ConditionalOnClass(RestHighLevelClient.class)
public class EsTcpClient {
	
	private static  String host;
	private static  int port;
    private static final String SCHEMA = "http";
//    private static final int CONNECT_TIME_OUT = 1000;
//    private static final int SOCKET_TIME_OUT = 30000;
//    private static final int CONNECTION_REQUEST_TIME_OUT = 500;
//
//    private static final int MAX_CONNECT_NUM = 100;
//    private static final int MAX_CONNECT_PER_ROUTE = 100;
    private static  String username;
    private static  String password;
    private static RestHighLevelClient client;
    
    private final static Logger logger = LoggerFactory.getLogger(EsTcpClient.class);
//    static {
//    	getClient();
//    }

    
    
    public static RestHighLevelClient getClient(){
    	if(client == null){
    		try {
    			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    			credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(username, password));
    			client = new RestHighLevelClient( RestClient.builder(new HttpHost(host, port, SCHEMA))
    					.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
    	                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
    	                        httpClientBuilder.disableAuthCaching();
    	                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    	                    }
    	                })
    					
    					);
    			//client = new RestHighLevelClient( RestClient.builder(new HttpHost(host, port, SCHEMA)));
			} catch (Exception e) {
				logger.error("ES初始化错误",e);
				e.printStackTrace();
			}
        }
        return client;
    }

	public static String getHost() {
		return host;
	}
    @Value("${elasticsearch.host:'localhost'}")
	public void setHost(String host) {
		EsTcpClient.host = host;
	}

	public static int getPort() {
		return port;
	}
	@Value("${elasticsearch.port:9200}")
	public void setPort(int port) {
		EsTcpClient.port = port;
	}
    
	public static String getUsername() {
		return username;
	}
	@Value("${elasticsearch.username:'elastic'}")
	public void setUsername(String username) {
		EsTcpClient.username = username;
	}

	public static String getPassword() {
		return password;
	}
	@Value("${elasticsearch.password:'elastic'}")
	public void setPassword(String password) {
		EsTcpClient.password = password;
	}

	public static void close() {
        if (client != null) {
            try {
            	client.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

}