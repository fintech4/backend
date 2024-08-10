package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockMetadataEntity;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import com.Toou.Toou.port.out.StockMetadataPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockMetadataAdapter implements StockMetadataPort {

	private final StockMetadataJpaRepository stockMetadataJpaRepository;

	@Override
	public List<StockMetadata> searchStocksByStockName(String stockName, int limit) {
		List<StockMetadataEntity> entities = stockMetadataJpaRepository.findByStockNameContaining(
				stockName);
		return entities.stream()
				.map(this::toDomainModel)
				.limit(limit)
				.collect(Collectors.toList());
	}

	@Override
	public StockMetadata findStockByStockCode(String stockCode) {
		StockMetadataEntity stockMetadataEntity = stockMetadataJpaRepository.findByStockCode(stockCode)
				.orElseThrow(() -> new CustomException(
						CustomExceptionDetail.STOCK_NOT_FOUND));

		return toDomainModel(stockMetadataEntity);
	}

	private StockMetadata toDomainModel(StockMetadataEntity entity) {
		return new StockMetadata(
				entity.getId(),
				entity.getStockCode(),
				entity.getStockName(),
				entity.getMarket()
		);
	}
}
