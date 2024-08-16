package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.domain.model.StockMetadata;
import com.Toou.Toou.port.out.AccountAssetPort;
import com.Toou.Toou.port.out.HoldingStockPort;
import com.Toou.Toou.port.out.StockHistoryPort;
import com.Toou.Toou.port.out.StockMetadataPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountHoldingService implements AccountHoldingUseCase {

	private final AccountAssetPort accountAssetPort;
	private final HoldingStockPort holdingStockPort;
	private final StockHistoryPort stockHistoryPort;
	private final StockMetadataPort stockMetadataPort;

	@Transactional
	@Override
	public Output execute(AccountHoldingUseCase.Input input) {
		AccountAsset accountAsset = accountAssetPort.findAssetByKakaoId(input.kakaoId);
		List<HoldingIndividualStock> holdings = holdingStockPort.findAllHoldingsByAccountAssetId(
				accountAsset.getId());

		Long totalHoldingsValue = 0L;
		Long totalInitialInvestment = 0L;  // 주식들 평균 매수금액 * 보유 수량의 합

		for (HoldingIndividualStock holding : holdings) {
			StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(holding.getStockCode());
			StockDailyHistory stockDailyHistory = stockHistoryPort.findStockHistoryByDate(
					stockMetadata.getId(), input.todayDate);

			Long newCurrentPrice = stockDailyHistory.getClosingPrice();

			// 평가 금액 = 현재 가격 * 보유 주식 수
			Long newValuation = newCurrentPrice * holding.getQuantity();
			totalHoldingsValue += newValuation;

			Long initialInvestment = holding.getAveragePurchasePrice() * holding.getQuantity();
			totalInitialInvestment += initialInvestment;

			// 수익률 = ((현재 가격 - 평균 매수가) / 평균 매수가) * 100
			Double newYield = ((double) (newCurrentPrice - holding.getAveragePurchasePrice())
					/ holding.getAveragePurchasePrice()) * 100;

			HoldingIndividualStock newHolding = new HoldingIndividualStock(
					holding.getId(),
					holding.getStockCode(),
					holding.getStockName(),
					holding.getAveragePurchasePrice(),
					newCurrentPrice,
					holding.getQuantity(),
					newValuation,
					newYield,
					holding.getAccountAssetId()
			);
			holdingStockPort.save(newHolding);
		}
		AccountAsset updatedAccountAsset = accountAsset.updateWithNewHoldingsData(totalHoldingsValue,
				totalInitialInvestment);
		AccountAsset savedAccountAsset = accountAssetPort.saveAsset(updatedAccountAsset);
		return new Output(holdings);
	}
}
