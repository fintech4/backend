package com.Toou.Toou.adapter.mysql.entity;


import com.Toou.Toou.domain.model.StockOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "holding_individual_stock")
public class HoldingStockEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "stock_code", nullable = false)
	private String stockCode;

	@Column(name = "stock_name", nullable = false)
	private String stockName;

	@Column(name = "average_purchase_price", nullable = false)
	private Long averagePurchasePrice;

	@Column(name = "current_price", nullable = false)
	private Long currentPrice;

	@Column(name = "quantity", nullable = false)
	private Long quantity;

	@Column(name = "valuation", nullable = false)
	private Long valuation;

	@Column(name = "yield", nullable = false)
	private Double yield;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_asset_id", nullable = false)
	private AccountAssetEntity accountAsset;

	public HoldingStockEntity(StockOrder stockOrder, AccountAssetEntity accountAssetEntity) {
		this.stockCode = stockOrder.getStockCode();
		this.stockName = stockOrder.getStockName();
		this.averagePurchasePrice = stockOrder.getStockPrice();
		this.currentPrice = stockOrder.getStockPrice();
		this.quantity = stockOrder.getOrderQuantity();
		this.valuation = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
		this.yield = 0.0; // 초기 수익률은 0으로 설정
		this.accountAsset = accountAssetEntity;
	}
}