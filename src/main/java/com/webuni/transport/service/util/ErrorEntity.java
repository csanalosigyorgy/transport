package com.webuni.transport.service.util;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorEntity {

	private LocalDateTime timestamp;
	private String message;
	private int errorCode;

	public ErrorEntity(String message, int errorCode) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.errorCode = errorCode;
	}
}
