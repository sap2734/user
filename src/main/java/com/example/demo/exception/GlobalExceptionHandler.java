package com.example.demo.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> handleCustomException(CustomException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		// body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
	    List<String> messages = ex.getConstraintViolations().stream()
	            .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
	         //   .map(cv -> cv.getMessage())
	            .collect(Collectors.toList());
	    return ResponseEntity.badRequest().body(messages);
	}
}
