package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockSellable;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface SellableStockUseCase {

	SellableStockUseCase.Output execute(SellableStockUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String stockCode;
		String providerId;
	}

	@AllArgsConstructor
	@Data
	class Output {

		StockSellable stockSellable;
	}
}
