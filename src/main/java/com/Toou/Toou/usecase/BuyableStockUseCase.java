package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockBuyable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface BuyableStockUseCase {

	BuyableStockUseCase.Output execute(BuyableStockUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String stockCode;
		LocalDate buyDate;
		String providerId;
	}

	@AllArgsConstructor
	@Data
	class Output {

		StockBuyable stockBuyable;
	}
}
