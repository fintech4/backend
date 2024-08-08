package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockMetadata;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListStockMetadataService implements ListStockMetadataUseCase {

	private static final List<StockMetadata> DUMMY_STOCK_METADATA_LIST = List.of(
			new StockMetadata(1L, "A079980", "휴비스"),
			new StockMetadata(2L, "A065510", "휴비츠"),
			new StockMetadata(3L, "A215090", "휴센텍"),
			new StockMetadata(4L, "A005010", "휴스틸")
	);

	@Override
	public Output execute(Input input) {
		return new Output(DUMMY_STOCK_METADATA_LIST);
		//TODO: input에서 주식 이름 받아와서 검색해서 리턴.
	}
}
