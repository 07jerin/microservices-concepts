package com.jerin.ws.filtering;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

	@GetMapping(path = "/filtering", produces = "application/json")
	public MappingJacksonValue filtering() {
		SomeBean bean = new SomeBean("F1", "F2", "F3");
		MappingJacksonValue result = CustomFilterProvider.filterAllExcept(bean, null, "userselection");
		return result;
	}

	@GetMapping(path = "/filtering-list", produces = "application/json")
	public MappingJacksonValue filteringList() {
		List<SomeBean> bean = Arrays.asList(new SomeBean("F1", "F2", "F3"), new SomeBean("F11", "F21", "F31"));
		MappingJacksonValue result = CustomFilterProvider.filterAllExcept(bean, null, "userselection");
		return result;

	}

	@GetMapping(path = "/filtering/{values}")
	public MappingJacksonValue getDynamincFilterResults(@PathVariable String values) {
		SomeBean bean = new SomeBean("F1", "F2", "F3");
		Set<String> valuesToInclude = new HashSet<>(Arrays.asList(values.split(",")));
		MappingJacksonValue result = CustomFilterProvider.filterAllExcept(bean, valuesToInclude, "userselection");
		return result;
	}

}
