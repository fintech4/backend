package com.Toou.Toou.exception;

import com.Toou.Toou.port.in.dto.CustomErrorResponse;
import com.Toou.Toou.port.in.dto.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException ex) {
		CustomErrorResponse errorResponse = new CustomErrorResponse(
				new ErrorDetails(ex.getType(), ex.getMessage()));
		return ResponseEntity.ok(errorResponse); //errorResponse 의 ok 값으로 에러 여부 구분.
	}
}
