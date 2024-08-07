package com.Toou.Toou;

import com.Toou.Toou.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationConfig.class)
public class ToouApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToouApplication.class, args);
	}

}
