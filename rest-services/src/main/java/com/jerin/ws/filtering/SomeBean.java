package com.jerin.ws.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@JsonFilter("userselection")
public class SomeBean {

	private String field1;

	@JsonIgnore
	private String field2;

	private String field3;
}
