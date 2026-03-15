package com.ApiGateway.ApiGateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("shop-route", r -> r.path("/shop/**")
                        .uri("lb://SHOP"))
                .route("wallet-route", r -> r.path("/wallet/**")
                        .uri("lb://WALLET"))
                .route("inventory-route", r -> r.path("/inventory/**")
                        .uri("lb://INVENTORY"))
                .build();
    }
}
