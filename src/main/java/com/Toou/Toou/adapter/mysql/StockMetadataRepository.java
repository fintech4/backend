package com.Toou.Toou.adapter.mysql;

import com.Toou.Toou.adapter.mysql.entity.StockMetadataEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMetadataRepository extends JpaRepository<StockMetadataEntity, Long> {

	List<StockMetadataEntity> findByStockNameContaining(String stockName);
}
