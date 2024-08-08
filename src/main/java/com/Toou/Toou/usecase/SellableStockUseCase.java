package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockSellable;
import com.Toou.Toou.domain.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface SellableStockUseCase {

	SellableStockUseCase.Output execute(SellableStockUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String stockCode;
		UserAccount userAccount;
	}

	@AllArgsConstructor
	@Data
	class Output {

		StockSellable stockSellable;
	}
}
