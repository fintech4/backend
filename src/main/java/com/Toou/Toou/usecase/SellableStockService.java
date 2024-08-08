package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockSellable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellableStockService implements SellableStockUseCase {

	private static final StockSellable stockSellable = new StockSellable(
			1L, "A079980", "휴비스", 10L
	);

	@Override
	public Output execute(Input input) {
		return new Output(stockSellable);
	}
}
