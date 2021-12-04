package com.jerin.microservices.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jerin.microservices.limitservice.bean.Limits;
import com.jerin.microservices.limitservice.configuration.LimitsConfiuration;

@RestController
public class LimitsController {

	@Autowired
	private LimitsConfiuration configuration;

	@GetMapping("/limits")
	public Limits retrieveLimits() {

		return new Limits(configuration.getMinimum(), configuration.getMaximum());

	}

}
