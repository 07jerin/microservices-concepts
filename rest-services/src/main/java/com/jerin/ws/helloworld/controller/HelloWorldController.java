package com.jerin.ws.helloworld.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jerin.ws.helloworld.models.HelloWorld;

@RestController
public class HelloWorldController {

	@Autowired
	MessageSource messageSource;

//	@GetMapping(path = "/hello-world")
	@RequestMapping(method = RequestMethod.POST, path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping(path = "/hello-world-bean")
	public HelloWorld helloWorldBean() {
		return new HelloWorld("Hello World From Bean");
	}

	@GetMapping(path = "/hello-world-bean/{name}")
	public HelloWorld greet(@PathVariable String name) {
		return new HelloWorld("Hello World : " + name);
	}

	@GetMapping(path = "/hello-i18n")
	public String hello(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		String message = messageSource.getMessage("messages.greet.hello", null, LocaleContextHolder.getLocale());
		return message;
	}

}
