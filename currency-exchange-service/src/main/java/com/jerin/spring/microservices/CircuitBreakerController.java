package com.jerin.spring.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
	@Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
	public String sampleAPI() {
		logger.info("Sample API call recieved");
		// This will fail
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/fail-url",
				String.class);
		return forEntity.getBody();
	}

	@GetMapping("/sample-api-2")
	@CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
	public String sampleAPI2() {
		logger.info("Sample API2 call recieved");
		// This will fail
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/fail-url",
				String.class);
		return forEntity.getBody();
	}

	private String hardCodedResponse(Exception ex) {
		return "Fallback Response " + ex.getMessage();
	}

	@GetMapping("/sample-api-3")
	@RateLimiter(name = "sample-api-3", fallbackMethod = "hardCodedResponse")
	public String sampleAPI3() {
		logger.info("Sample API3 call recieved");
		return "Sample API 3";

	}

	@GetMapping("/sample-api-4")
	@Bulkhead(name = "sample-api-4", fallbackMethod = "hardCodedResponse")
	public String sampleAPI4() {
		logger.info("Sample API4 call recieved");
		return "Sample API 4";

	}

}
