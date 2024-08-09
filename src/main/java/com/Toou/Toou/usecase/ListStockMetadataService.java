package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.port.out.StockMetadataPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListStockMetadataService implements ListStockMetadataUseCase {

	private final StockMetadataPort stockMetadataPort;

	@Override
	public Output execute(Input input) {
		List<StockMetadata> stockMetadatas = stockMetadataPort.searchStocksByStockName(
				input.getStockName(), input.limit);
		return new Output(stockMetadatas);
	}
}
