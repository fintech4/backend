package com.Toou.Toou.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		List<Server> servers = createServers();

		return new OpenAPI()
				.info(apiInfo())
				.servers(servers);
//				.addSecurityItem(createSecurityRequirement())  //TODO: 인증 구현 이후 주석 해제
//				.components(createComponents());
	}

	private List<Server> createServers() {
		List<Server> servers = new ArrayList<>();
		servers.add(new Server().url("http://toou.kro.kr/"));
		servers.add(new Server().url("http://localhost:8080/"));
		return servers;
	}

	private Info apiInfo() {
		return new Info()
				.title("Toou API 명세")
				.description("잘못된 부분이나 오류 발생 시 바로 말씀해주세요.") // 문서 설명
				.version("0.0.1");
	}

	private SecurityRequirement createSecurityRequirement() {
		String jwtSchemeName = "Authorization";
		return new SecurityRequirement().addList(jwtSchemeName);
	}

	private Components createComponents() {
		String jwtSchemeName = "Authorization";
		return new Components().addSecuritySchemes(jwtSchemeName,
				new SecurityScheme()
						.name(jwtSchemeName)
						.in(SecurityScheme.In.HEADER)
						.type(SecurityScheme.Type.APIKEY));
	}
}
