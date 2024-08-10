package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.HoldingStockEntity;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.exception.CustomException;
import com.Toou.Toou.exception.CustomExceptionDetail;
import com.Toou.Toou.port.out.HoldingStockPort;
import java.util.List;
import java.util.Optional;
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
	public Optional<HoldingIndividualStock> findHoldingByStockCodeAndAssetId(String StockCode,
			Long assetId) {
		HoldingStockEntity entity = holdingStockJpaRepository.findByStockCodeAndAccountAssetId(
				StockCode, assetId).orElseThrow(
				() -> new CustomException(CustomExceptionDetail.NO_HOLDING_STOCK)
		);
		return Optional.of(toDomainModel(entity));
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
				entity.getYield()
		);
	}
}
