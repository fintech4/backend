package com.Toou.Toou.adapter.mysql.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "account_asset")
public class AccountAssetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "kakao_id", nullable = false)
	private String kakaoId;

	@Column(name = "total_asset", nullable = false)
	private Long totalAsset;

	@Column(name = "deposit", nullable = false)
	private Long deposit;

	@Column(name = "total_holdings_value", nullable = false)
	private Long totalHoldingsValue;

	@Column(name = "total_holdings_quantity", nullable = false)
	private Long totalHoldingsQuantity;

	@Column(name = "investment_yield", nullable = false)
	private Double investmentYield;

	@Column(name = "total_principal", nullable = false)
	private Long totalPrincipal;

}