package com.jerin.spring.microservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

	@Autowired
	private Environment environment;

	@Autowired
	private CurrencyExchangeRepository repository;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getCurrencyExchange(@PathVariable String from, @PathVariable String to) {

		logger.info("getCurrencyExchange called with {} to {}", from, to);
		CurrencyExchange exchange = repository.findByFromAndTo(from, to);
		String port = environment.getProperty("local.server.port");

		String host = environment.getProperty("HOSTNAME");
		String version = "v12";

		exchange.setEnvironment(port + " " + version + " " + host);
		return exchange;
	}
}
