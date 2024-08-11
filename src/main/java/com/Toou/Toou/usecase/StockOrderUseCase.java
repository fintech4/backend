package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.TradeType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface StockOrderUseCase {

	StockOrderUseCase.Output execute(StockOrderUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String stockCode;
		LocalDate orderDate;
		String kakaoId;
		TradeType tradeType;
		Long orderQuantity;
	}

	@AllArgsConstructor
	@Data
	class Output {

	}
}
