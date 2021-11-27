package com.integration.cross;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
@Configuration
public class GateWayCorsConfig {

	@Bean
	public CorsFilter corsFilter() {
/*		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		// corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(source);*/
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        final CorsConfiguration config = new CorsConfiguration();  
        config.setAllowCredentials(true);  
        config.addAllowedOrigin("*");  
        config.addAllowedHeader("*");  
        //config.addAllowedMethod("OPTIONS");  
        //config.addAllowedMethod("HEAD");  
        //config.addAllowedMethod("GET");  
        //config.addAllowedMethod("PUT");  
        //config.addAllowedMethod("POST");  
        //config.addAllowedMethod("DELETE");  
        //config.addAllowedMethod("PATCH");  
        config.addAllowedMethod("*");  
        source.registerCorsConfiguration("/**", config);  
        return new CorsFilter(source);  
	}

    public static void main(String[] args) {
        BloomFilter bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),100000,0.01);
        bf.put("10086");
        bf.put("100861");
        bf.put("100862");
        System.out.println(bf.mightContain("123456"));
        System.out.println(bf.mightContain("100863"));
    }

}
