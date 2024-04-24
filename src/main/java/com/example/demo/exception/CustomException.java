package com.example.demo.exception;

@SuppressWarnings("serial")

public class CustomException extends RuntimeException {
		
	public CustomException(String message) {
		super(message);
		}
	
	public CustomException() {
		super();
		}
	
}