package com.my.exception;

public class AddException extends Exception {

	public AddException() {
		//부모생성자 호출
		super();
		
	}

	public AddException(String message) {
		
		super(message);
		
	}

}
