package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.port.out.AccountAssetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountAssetService implements AccountAssetUseCase {

	private final AccountAssetPort accountAssetPort;

	@Transactional
	@Override
	public AccountAssetUseCase.Output execute(AccountAssetUseCase.Input input) {
		AccountAsset accountAsset = accountAssetPort.findAssetByKakaoId(input.kakaoId);
		return new Output(accountAsset);
	}
}
