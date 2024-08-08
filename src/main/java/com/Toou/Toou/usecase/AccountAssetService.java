package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountAssetService implements AccountAssetUseCase {

	private static final AccountAsset DUMMY_ACCOUNT_ASSET = AccountAsset.builder()
			.id(1L)
			.deposit(50000000L)
			.investmentYield(12.56)
			.totalAsset(150000000L)
			.totalHoldingsQuantity(3L)
			.totalHoldingsValue(7000000L)
			.build();

	@Transactional
	@Override
	public AccountAssetUseCase.Output execute(AccountAssetUseCase.Input input) {
		return new Output(DUMMY_ACCOUNT_ASSET);
	}
}
