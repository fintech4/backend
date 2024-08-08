package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockMetadata;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface ListStockMetadataUseCase {

	Output execute(Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String stockName;
		int limit;
	}

	@AllArgsConstructor
	@Data
	class Output {

		List<StockMetadata> stokMetadataList;
	}
}
