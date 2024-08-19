package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.HoldingIndividualStock;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface AccountHoldingUseCase {

	AccountHoldingUseCase.Output execute(AccountHoldingUseCase.Input input);

	@AllArgsConstructor
	@Data
	class Input {

		String providerId;
		LocalDate todayDate;
	}

	@AllArgsConstructor
	@Data
	class Output {

		List<HoldingIndividualStock> holdings;
	}
}
