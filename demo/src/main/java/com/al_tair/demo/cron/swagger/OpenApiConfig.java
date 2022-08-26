package com.al_tair.demo.cron.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Al_tair
 * @date 2022/7/20-11:12
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI lnsFileOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("cron表达式生成和解析案例")
                        .description("便于开发的快速上手和使用")
                        .version("version 1.0")
                        .license(new License().name("MIT").url("http://springdoc.org")));
    }
}
