package com.Toou.Toou.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	private final String frontProdIp;
	private final String frontLocalIp;
	private final String backProdIp;

	public CorsConfig(
			@Value("${front-prod-ip}") String frontProdIp,
			@Value("${front-local-ip}") String frontLocalIp,
			@Value(("${back-prod-ip}")) String backProdIp) {
		this.frontProdIp = frontProdIp;
		this.frontLocalIp = frontLocalIp;
		this.backProdIp = backProdIp;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		List<String> allowedOrigins = Arrays.asList(frontProdIp, frontLocalIp, backProdIp);

		config.setAllowCredentials(true);
		allowedOrigins.forEach(config::addAllowedOrigin);
		config.addAllowedHeader("*");
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
