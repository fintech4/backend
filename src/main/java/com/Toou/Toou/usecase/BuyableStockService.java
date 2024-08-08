package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockBuyable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyableStockService implements BuyableStockUseCase {

	private static final StockBuyable stockBuyable = new StockBuyable(
			1L, "A079980", "휴비스", 5670L, 100000L, 10L
	);

	@Override
	public Output execute(Input input) {
		return new Output(stockBuyable);
	}
}
