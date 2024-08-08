package com.Toou.Toou.port.in.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDetails {


	@Schema(description = "에러 타입")
	private final String type;

	@Schema(description = "에러 설명")
	private final String message;
}