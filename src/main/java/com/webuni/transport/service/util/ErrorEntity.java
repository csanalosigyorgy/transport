package com.webuni.transport.service.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.FieldError;

import lombok.Data;

@Data
public class ErrorEntity {

	private LocalDateTime timestamp;
	private String message;
	private int errorCode;
	private List<FieldError> fieldErrors;

	public ErrorEntity(String message, int errorCode) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.errorCode = errorCode;
	}
}
