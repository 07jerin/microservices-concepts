package com.jerin.spring.microservices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping(value = "currency-conversion/from/{from}/to/{to}/quantity/{qty}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal qty) {

		Map<String, Object> params = new HashMap<>();
		params.put("from", from);
		params.put("to", to);
		ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, params);
		CurrencyConversion conversion = response.getBody(); // This works as the structure of the response from other
															// service is same

		conversion.setEnvironment(conversion.getEnvironment() + " Rest Template");
		conversion.setQuantity(qty);
		conversion.setTotalCalculatedAmount(qty.multiply(conversion.getConversionMultiple()));

		return conversion;

	}

	@GetMapping(value = "currency-conversion-feign/from/{from}/to/{to}/quantity/{qty}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal qty) {

		CurrencyConversion conversion = currencyExchangeProxy.getCurrencyExchange(from, to);
		conversion.setEnvironment(conversion.getEnvironment() + " Feign");
		conversion.setQuantity(qty);
		conversion.setTotalCalculatedAmount(qty.multiply(conversion.getConversionMultiple()));

		return conversion;

	}

}
