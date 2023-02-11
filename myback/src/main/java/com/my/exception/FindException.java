package com.my.exception;

public class FindException extends Exception {

	public FindException() {
		//부모생성자 호출
		super();
		
	}

	public FindException(String message) {
		
		super(message);
		
	}

}
