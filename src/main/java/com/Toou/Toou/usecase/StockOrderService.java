package com.Toou.Toou.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockOrderService implements StockOrderUseCase {

	@Override
	public Output execute(Input input) {
		return new Output();
	}
}
