package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockBuyable;
import com.Toou.Toou.domain.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface BuyableStockUseCase {

	BuyableStockUseCase.Output execute(BuyableStockUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String stockCode;
		UserAccount userAccount;
	}

	@AllArgsConstructor
	@Data
	class Output {

		StockBuyable stockBuyable;
	}
}
