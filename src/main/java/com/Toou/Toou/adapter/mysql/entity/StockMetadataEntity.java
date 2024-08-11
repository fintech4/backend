package com.Toou.Toou.adapter.mysql.entity;

import com.Toou.Toou.domain.model.MarketType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "stock_metadata")
public class StockMetadataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "stock_code", nullable = false, unique = true)
	private String stockCode;

	@Column(name = "stock_name", nullable = false, unique = true)
	private String stockName;

	@Enumerated(EnumType.STRING)
	@Column(name = "market", nullable = false)
	private MarketType market;

}
