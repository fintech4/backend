package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.port.out.HoldingStockPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountHoldingService implements AccountHoldingUseCase {

	private final HoldingStockPort holdingStockPort;

	@Transactional
	@Override
	public AccountHoldingUseCase.Output execute(AccountHoldingUseCase.Input input) {
		List<HoldingIndividualStock> holdings = holdingStockPort.findAllHoldings(input.accountAsset);
		return new Output(holdings);
	}
}
