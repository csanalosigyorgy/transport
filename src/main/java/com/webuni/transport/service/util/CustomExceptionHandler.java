package com.webuni.transport.service.util;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorEntity> handleMethodArgumentNotValid(MethodArgumentNotValidException e, WebRequest request){
		log.warn(e.getMessage(), e);
		ErrorEntity error = new ErrorEntity(e.getMessage(), 1003);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(error);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorEntity> handleIllegalArgument(IllegalArgumentException e, WebRequest request){
		log.warn(e.getMessage(), e);
		ErrorEntity error = new ErrorEntity(e.getMessage(), 1001);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(error);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorEntity> handleEntityNotFound(NoSuchElementException e, WebRequest request){
		log.warn(e.getMessage(), e);
		ErrorEntity error = new ErrorEntity(e.getMessage(), 1002);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(error);
	}
}
