package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.TradeType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockOrderRequest {

	@NotNull
	private Long stockPrice;

	@NotNull
	private Long orderQuantity;

	@NotNull
	private TradeType tradeType;
}
