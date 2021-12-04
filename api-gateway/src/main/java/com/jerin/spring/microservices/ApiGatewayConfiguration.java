package com.jerin.spring.microservices;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		Function<PredicateSpec, Buildable<Route>> routeFunction = p -> p.path("/get")
				.filters(f -> f.addRequestHeader("My-Header", "My-URI")
						.addRequestHeader("My-New-Header", "My-New-URI")) // Filter operations
				.uri("http://httpbin.org:80"); // re direction URI

		return builder.routes()
				.route(routeFunction) // will re route the path based on the provided function
				.route(p -> p.path("/currency-exchange/**")// For all Urls coming from here
						.uri("lb://currency-exchange")) // Redirect via eureka server LB
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
				.build();
	}
}
