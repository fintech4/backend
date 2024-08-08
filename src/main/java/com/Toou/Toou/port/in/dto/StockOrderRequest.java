package com.Toou.Toou.port.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockOrderRequest {

	private Long stockPrice;
	private Long orderQuantity;
}
