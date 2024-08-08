package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.TradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockOrderRequest {

	@Schema(description = "주식 가격")
	@NotNull
	private Long stockPrice;

	@Schema(description = "주문하려는 수량")
	@NotNull
	private Long orderQuantity;

	@Schema(description = "주문 타입(매수/매도)")
	@NotNull
	private TradeType tradeType;
}
