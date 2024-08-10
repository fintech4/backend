package com.Toou.Toou.adapter.mysql.entity;

import com.Toou.Toou.adapter.mysql.AccountAssetJpaRepository;
import com.Toou.Toou.adapter.mysql.HoldingStockJpaRepository;
import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.port.out.HoldingStockPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoldingStockAdapter implements HoldingStockPort {

	private final AccountAssetJpaRepository accountAssetJpaRepository;
	private final HoldingStockJpaRepository holdingStockJpaRepository;

	@Override
	public List<HoldingIndividualStock> findAllHoldings(AccountAsset accountAsset) {
		List<HoldingStockEntity> holdingStockEntities = holdingStockJpaRepository.findByAccountAssetId(
				accountAsset.getId());
		return holdingStockEntities.stream()
				.map(this::toDomainModel)
				.collect(Collectors.toList());
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
