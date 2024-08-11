package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.HoldingStockEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoldingStockJpaRepository extends JpaRepository<HoldingStockEntity, Long> {

	List<HoldingStockEntity> findByAccountAssetId(Long accountAssetId);

	Optional<HoldingStockEntity> findByStockCodeAndAccountAssetId(String stockCode,
			Long accountAssetId);
}
