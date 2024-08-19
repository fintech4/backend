package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AccountAsset {

	private Long id;
	private OAuthProvider oauthProvider; // OAuth 제공자 타입
	private String providerId; // OAuth 제공자에서의 유저 ID
	private Long totalAsset;
	private Long deposit;
	private Long totalHoldingsValue;
	private Long totalHoldingsQuantity;
	private Double investmentYield;
	private Long totalPrincipal;

	public AccountAsset updateWhenBuyStock(StockOrder stockOrder, boolean isFirstBuy) {
		Long totalOrderValue = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
		Long newDeposit = this.deposit - totalOrderValue;
		Long newTotalHoldingsValue = this.totalHoldingsValue + totalOrderValue;
		Long newTotalHoldingsQuantity = isFirstBuy
				? this.totalHoldingsQuantity + 1
				: this.totalHoldingsQuantity;
		Long newPrincipal = this.totalPrincipal + newTotalHoldingsValue;

		return new AccountAsset(
				this.id,
				this.oauthProvider,
				this.providerId,
				this.totalAsset,
				newDeposit,
				newTotalHoldingsValue,
				newTotalHoldingsQuantity,
				this.investmentYield,
				newPrincipal
		);
	}


	public AccountAsset updateWhenSellStock(StockOrder stockOrder, boolean isLastStockSold) {
		Long totalOrderValue = stockOrder.getStockPrice() * stockOrder.getOrderQuantity();
		Long newDeposit = this.deposit + totalOrderValue;
		Long newTotalHoldingsValue = this.totalHoldingsValue - totalOrderValue;
		Long newTotalHoldingsQuantity = isLastStockSold
				? this.totalHoldingsQuantity - 1
				: this.totalHoldingsQuantity;
		Long newPrincipal = this.totalPrincipal - newTotalHoldingsValue;

		return new AccountAsset(
				this.id,
				this.oauthProvider,
				this.providerId,
				this.totalAsset,
				newDeposit,
				newTotalHoldingsValue,
				newTotalHoldingsQuantity,
				this.investmentYield,
				newPrincipal
		);
	}

	public AccountAsset updateWithNewHoldingsData(Long totalHoldingsValue) {
		long newTotalAsset = this.deposit + totalHoldingsValue;
		double newInvestmentYield =
				((double) (newTotalAsset - this.totalPrincipal) / this.totalPrincipal) * 100;

		return new AccountAsset(
				this.id,
				this.oauthProvider,
				this.providerId,
				newTotalAsset,
				this.deposit,
				totalHoldingsValue,
				this.totalHoldingsQuantity,
				newInvestmentYield,
				this.totalPrincipal
		);
	}

}
