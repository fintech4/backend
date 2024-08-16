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

		for (HoldingIndividualStock holding : holdings) {
			StockMetadata stockMetadata = stockMetadataPort.findStockByStockCode(holding.getStockCode());
			StockDailyHistory stockDailyHistory = stockHistoryPort.findStockHistoryByDate(
					stockMetadata.getId(),
					input.todayDate); //TODO: DB에 저장한 값이 최신 값이 아닐 경우, open api로 값을 저장해야함
			HoldingIndividualStock newHolding = holding.updateWithNewHoldingsData(
					stockDailyHistory.getClosingPrice());
			holdingStockPort.save(newHolding);
		}
		AccountAsset updatedAccountAsset = accountAsset.updateWithNewHoldingsData(totalHoldingsValue);
		AccountAsset savedAccountAsset = accountAssetPort.saveAsset(updatedAccountAsset);
		return new Output(holdings);
	}
}
