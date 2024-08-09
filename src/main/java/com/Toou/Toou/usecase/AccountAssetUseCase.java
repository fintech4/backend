package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface AccountAssetUseCase {

	AccountAssetUseCase.Output execute(AccountAssetUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String kakaoId;
	}

	@AllArgsConstructor
	@Data
	class Output {

		AccountAsset accountAsset;
	}
}
