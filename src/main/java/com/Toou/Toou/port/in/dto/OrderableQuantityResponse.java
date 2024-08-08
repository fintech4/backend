package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.StockBuyable;
import com.Toou.Toou.domain.model.StockSellable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderableQuantityResponse {

	private final Boolean ok;

	@Schema(description = "종목명")
	private String stockName;

	@Schema(description = "매수/매도 가능 수량")
	private Long quantity;

	public static OrderableQuantityResponse fromDomainModel(StockSellable domainModel) {
		return new OrderableQuantityResponse(
				true,
				domainModel.getStockName(),
				domainModel.getSellableQuantity()
		);
	}

	public static OrderableQuantityResponse fromDomainModel(StockBuyable domainModel) {
		return new OrderableQuantityResponse(
				true,
				domainModel.getStockName(),
				domainModel.getBuyableQuantity()
		);
	}
}
