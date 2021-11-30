package com.jerin.ws.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GenericExceptionResponse {

	private LocalDateTime timeStamp;
	private String message;
	private String details;

}
