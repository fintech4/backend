package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface AccountHoldingUseCase {

	AccountHoldingUseCase.Output execute(AccountHoldingUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		AccountAsset accountAsset;
	}

	@AllArgsConstructor
	@Data
	class Output {

		List<HoldingIndividualStock> holdings;
	}
}
