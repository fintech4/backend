package com.Toou.Toou.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VoidResponse {

	private final Boolean ok;

	public static VoidResponse ok() {
		return new VoidResponse(Boolean.TRUE);
	}
}
