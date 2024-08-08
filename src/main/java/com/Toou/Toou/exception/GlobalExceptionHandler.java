package com.Toou.Toou.exception;

import com.Toou.Toou.port.in.dto.CustomErrorResponse;
import com.Toou.Toou.port.in.dto.ErrorDetails;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidFormatException.class) // Enum 변환 실패할때.
	public ResponseEntity<CustomErrorResponse> handleInvalidFormatException(
			InvalidFormatException ex) {
		String fieldName = ex.getPath().get(0).getFieldName();
		String errorMessage = "";

		Class<?> targetType = ex.getTargetType();
		if (targetType.isEnum()) {
			String validValues = "";
			validValues = String.join(", ", Arrays.stream(targetType.getEnumConstants())
					.map(Object::toString)
					.toArray(String[]::new));
			errorMessage = "요청의 다음 속성의 값이 유효한 값이 아닙니다. 속성: " + fieldName + ", 값: " + ex.getValue() +
					". 가능한 값: " + validValues;
		} else {
			errorMessage = "요청의 다음 속성의 값이 유효한 값이 아닙니다. 속성 : " + fieldName + ", 값 : " + ex.getValue();
		}

		CustomErrorResponse response = new CustomErrorResponse(
				new ErrorDetails("validation_fail", errorMessage));
		return ResponseEntity.ok().body(response);
	}
}
