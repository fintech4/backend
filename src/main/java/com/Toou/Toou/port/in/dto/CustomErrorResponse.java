package com.Toou.Toou.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomErrorResponse {

	private final Boolean ok = false;
	private final ErrorDetails error;
}