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
	private String kakaoId;
	private Long totalAsset;
	private Long deposit;
	private Long totalHoldingsValue;
	private Long totalHoldingsQuantity;
	private Double investmentYield;

	public AccountAsset updateWhenBuyStock(Long totalPrice, boolean isFirstBuy) {
		return new AccountAsset(
				this.id,
				this.kakaoId,
				this.totalAsset,
				this.deposit - totalPrice,
				this.totalHoldingsValue + totalPrice,
				isFirstBuy ? this.totalHoldingsQuantity + 1 : this.totalHoldingsQuantity,
				this.investmentYield
		);
	}


	public AccountAsset updateWhenSellStock(Long totalPrice, boolean isLastStockSold) {
		return new AccountAsset(
				this.id,
				this.kakaoId,
				this.totalAsset,
				this.deposit + totalPrice,
				this.totalHoldingsValue - totalPrice,
				isLastStockSold ? this.totalHoldingsQuantity - 1 : this.totalHoldingsQuantity,
				this.investmentYield
		);
	}

	public AccountAsset updateWithNewHoldingsData(Long totalHoldingsValue,
			Long totalInitialInvestment) {
		long newTotalAsset = this.deposit + totalHoldingsValue;
		long initialTotalAsset = this.deposit + totalInitialInvestment;
		double newInvestmentYield =
				((double) (newTotalAsset - initialTotalAsset) / initialTotalAsset) * 100;
		return new AccountAsset(
				this.id,
				this.kakaoId,
				newTotalAsset,
				this.deposit,
				totalHoldingsValue,
				this.totalHoldingsQuantity,
				newInvestmentYield
		);
	}

}
