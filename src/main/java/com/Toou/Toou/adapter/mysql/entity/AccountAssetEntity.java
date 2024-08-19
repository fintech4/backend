package com.Toou.Toou.adapter.mysql.entity;


import com.Toou.Toou.domain.model.OAuthProvider;
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
@Entity(name = "account_asset")
public class AccountAssetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "oauth_provider", nullable = false)
	private OAuthProvider oauthProvider;

	@Column(name = "provider_id", nullable = false)
	private String providerId;

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