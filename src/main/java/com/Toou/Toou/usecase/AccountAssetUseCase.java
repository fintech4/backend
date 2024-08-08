package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface AccountAssetUseCase {

	AccountAssetUseCase.Output execute(AccountAssetUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		UserAccount userAccount;
	}

	@AllArgsConstructor
	@Data
	class Output {

		AccountAsset accountAsset;
	}
}
