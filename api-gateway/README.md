To handle common  features for all microservices
Now we use spring cloud gateway for this, earlier we used to use Ribbon


## Steps ##
1. Include the starter gateway and eureka client and create the api-gateway service
2. Register the service to eureka
3. Include the following in application properties, to enable the transfer to the service via API gateway
    > **spring.cloud.gateway.discovery.locator.enabled=true** 
4. Now we can access the URL using the API gateway and the application name exposed in the eureka server
    - http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/from/USD/to/INR
    - "Api gateway / Application name exposed in eureka/url of the application>
5. Application name in eureka is in caps, to use lower case we can use the following properties in api-gateway
    > **spring.cloud.gateway.discovery.locator.lower-case-service-id=true**
6. Make the URL even better by using re rerouting


## Build Custom Routes ##
1. Create a custom configuration class
2. Create RouteLocator bean and Add filters and redirection URL
3. We can use the above to make our URLs via gateway better 
  - Rather than  having to specify the eureka name service directly we can configure it in the router
    ```
			.route(p -> p.path("/currency-exchange/**") // For all Urls coming from here
				.uri("lb://currency-exchange")) // Redirect via eureka server LB
    ```
   - After this we can disable the following properties
		>**#spring.cloud.gateway.discovery.locator.enabled=true**
		>**#spring.cloud.gateway.discovery.locator.lower-case-service-id=true**
		
		
	- After this change, instead of
			> http://localhost:8765/currency-exchange/currency-exchange/from/USD/to/INR
		  We can simply use 
			> http://localhost:8765/currency-exchange/from/USD/to/INR
	
	
```java
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
				.route(p -> p.path("/currency-conversion-new/**") // To add a new URL mapping
						.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}
}

```
	
## Global Filtering ##

1. Create class implementing Global filter
2. override the filter method

```java
@Component
public class LogginFilter implements GlobalFilter {

	private Logger logger = LoggerFactory.getLogger(LogginFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("path of request recieved -> {}", exchange.getRequest()
				.getPath());
		return chain.filter(exchange);
	}

}
```

