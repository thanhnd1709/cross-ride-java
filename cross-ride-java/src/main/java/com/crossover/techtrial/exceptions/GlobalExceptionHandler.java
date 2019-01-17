package com.crossover.techtrial.exceptions;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Method argument not valid exception", ex);
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		List<String> message = new ArrayList<>();
		for (FieldError e : errors){
            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
        }
		String bodyOfResponse = message.toString();
		return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Message not readable exception", ex);
		return handleExceptionInternal(ex, "Please enter correct Date Time format", headers, status, request);
	}
	
	@ExceptionHandler(value= {RideException.class}) 
	public ResponseEntity<Object> handleRideException(RideException exception) {
		// general exception
		log.error("Exception: Ride exception ", exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	/**
	 * Global Exception handler for all exceptions.
	 */
	@ExceptionHandler
	public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(Exception exception) {
		// general exception
		log.error("Exception: Unable to process this request. ", exception);
		AbstractMap.SimpleEntry<String, String> response = new AbstractMap.SimpleEntry<>("message",
				"Unable to process this request.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
