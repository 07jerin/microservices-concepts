package com.jerin.spring.microservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Application name to which we need to make call, and URL - hosted location
//@FeignClient(name = "currency-exchange", url = "localhost:8000")

//For load balancing, once the Eureka service registry is done, we can remove the url
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

	// These will be exact URLs as provided in the target service
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion getCurrencyExchange(@PathVariable String from, @PathVariable String to);

}
