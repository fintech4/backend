package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.domain.model.UserAccount;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface AccountHoldingUseCase {

	AccountHoldingUseCase.Output execute(AccountHoldingUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		UserAccount userAccount;
	}

	@AllArgsConstructor
	@Data
	class Output {

		List<HoldingIndividualStock> holdings;
	}
}
