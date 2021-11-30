package com.jerin.ws.filtering;

import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class CustomFilterProvider {

	public static MappingJacksonValue filterAllExcept(Object objectToFilter, Set<String> valuesToInclude, String name) {
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = valuesToInclude != null
				? SimpleBeanPropertyFilter.filterOutAllExcept(valuesToInclude)
				: SimpleBeanPropertyFilter.serializeAll();
		FilterProvider filters = new SimpleFilterProvider().addFilter(name, simpleBeanPropertyFilter);
		MappingJacksonValue value = new MappingJacksonValue(objectToFilter);
		value.setFilters(filters);
		return value;
	}
}
