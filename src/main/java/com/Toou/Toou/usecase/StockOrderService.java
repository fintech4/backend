package com.Toou.Toou.usecase;

import com.Toou.Toou.port.out.StockOrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockOrderService implements StockOrderUseCase {

	private final StockOrderPort stockOrderPort;

	@Override
	public Output execute(Input input) {
		stockOrderPort.orderStock(input.stockOrder, input.accountAsset);
		return new Output();
	}
}
