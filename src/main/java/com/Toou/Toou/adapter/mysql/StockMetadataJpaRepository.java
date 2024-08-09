package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockMetadataEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMetadataJpaRepository extends JpaRepository<StockMetadataEntity, Long> {

	List<StockMetadataEntity> findByStockNameContaining(String stockName);

	Optional<StockMetadataEntity> findByStockCode(String stockCode);
}
