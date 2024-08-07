package com.Toou.Toou.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.Toou.Toou.adapter.mysql.entity")
@EnableJpaRepositories(basePackages = "com.Toou.Toou.adapter.mysql")
public class ApplicationConfig {

}
