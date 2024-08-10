package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.StockOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface StockOrderUseCase {

	StockOrderUseCase.Output execute(StockOrderUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		StockOrder stockOrder;
		AccountAsset accountAsset;
	}

	@AllArgsConstructor
	@Data
	class Output {

	}
}
