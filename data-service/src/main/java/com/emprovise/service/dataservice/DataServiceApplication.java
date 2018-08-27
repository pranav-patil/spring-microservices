package com.emprovise.service.dataservice;

import com.emprovise.service.dataservice.dto.StockDetail;
import com.emprovise.service.dataservice.resource.StockResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@EnableJpaRepositories(basePackages = "com.emprovise.service.dataservice.repository")
@SpringBootApplication
@EnableDiscoveryClient
public class DataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }

    @Bean
    RouterFunction<?> routes(StockResource stockResource) {

        return nest(path("/stocks"),
                route(RequestPredicates.GET("/name/{name}"),
                        request -> ok().body(stockResource.findByName(request.pathVariable("name")), StockDetail.class))
                .andRoute(GET("/all"),
                        request -> ok().body(stockResource.all(), StockDetail.class))
        );
    }
}
