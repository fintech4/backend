package com.Toou.Toou.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

	private final String type;
	private final String message;

	public CustomException(CustomExceptionDetail exceptionDetail) {
		super(exceptionDetail.getMessage());
		this.type = exceptionDetail.getType();
		this.message = exceptionDetail.getMessage();
	}
}
