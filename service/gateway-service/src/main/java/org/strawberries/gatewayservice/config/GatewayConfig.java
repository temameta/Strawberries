package org.strawberries.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("http://localhost:8082"))
                .route("order-service", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("http://localhost:8083"))
                .route("cart-service", r -> r
                        .path("/api/cart/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("http://localhost:8084"))
                .build();
    }
}