package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockBuyable;
import com.Toou.Toou.port.out.BuyableStockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyableStockService implements BuyableStockUseCase {

	private final BuyableStockPort buyableStockPort;

	@Override
	public Output execute(Input input) {
		StockBuyable stockBuyable = buyableStockPort.getStockBuyableByStockCode(input.stockCode,
				input.buyDate, input.accountAsset);
		return new Output(stockBuyable);
	}
}
