package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.HoldingStockEntity;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.port.out.HoldingStockPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoldingStockAdapter implements HoldingStockPort {

	private final HoldingStockJpaRepository holdingStockJpaRepository;

	@Override
	public List<HoldingIndividualStock> findAllHoldingsByAccountAssetId(Long accountAssetId) {
		List<HoldingStockEntity> holdingStockEntities = holdingStockJpaRepository.findByAccountAssetId(
				accountAssetId);
		return holdingStockEntities.stream()
				.map(this::toDomainModel)
				.collect(Collectors.toList());
	}

	@Override
	public HoldingIndividualStock findHoldingByStockCodeAndAssetId(String StockCode,
			Long assetId) {
		HoldingStockEntity entity = holdingStockJpaRepository.findByStockCodeAndAccountAssetId(
				StockCode, assetId).orElse(null);
		return entity == null ? null : toDomainModel(entity);
	}

	@Override
	public HoldingIndividualStock save(HoldingIndividualStock holdingIndividualStock) {
		HoldingStockEntity entity = toEntity(holdingIndividualStock);
		HoldingStockEntity savedEntity = holdingStockJpaRepository.save(entity);
		return toDomainModel(savedEntity);
	}

	@Override
	public void delete(HoldingIndividualStock holdingIndividualStock) {
		HoldingStockEntity entity = toEntity(holdingIndividualStock);
		holdingStockJpaRepository.delete(entity);
	}

	private HoldingIndividualStock toDomainModel(HoldingStockEntity entity) {
		return new HoldingIndividualStock(
				entity.getId(),
				entity.getStockCode(),
				entity.getStockName(),
				entity.getAveragePurchasePrice(),
				entity.getCurrentPrice(),
				entity.getQuantity(),
				entity.getValuation(),
				entity.getYield(),
				entity.getAccountAssetId()
		);
	}

	private HoldingStockEntity toEntity(HoldingIndividualStock holdingIndividualStock) {
		return new HoldingStockEntity(
				holdingIndividualStock.getId(),
				holdingIndividualStock.getStockCode(),
				holdingIndividualStock.getStockName(),
				holdingIndividualStock.getAveragePurchasePrice(),
				holdingIndividualStock.getCurrentPrice(),
				holdingIndividualStock.getQuantity(),
				holdingIndividualStock.getValuation(),
				holdingIndividualStock.getYield(),
				holdingIndividualStock.getAccountAssetId()
		);
	}
}
