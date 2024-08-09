package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockMetadataEntity;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.port.out.StockMetadataPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//@Repository
@Component
@RequiredArgsConstructor
public class StockMetadataAdapter implements StockMetadataPort {

	private final StockMetadataRepository stockMetadataRepository;

	@Override
	public List<StockMetadata> searchStocksByStockName(String stockName, int limit) {
		List<StockMetadataEntity> entities = stockMetadataRepository.findByStockNameContaining(
				stockName);
		return entities.stream()
				.map(this::toDomainModel)
				.limit(limit)
				.collect(Collectors.toList());
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
