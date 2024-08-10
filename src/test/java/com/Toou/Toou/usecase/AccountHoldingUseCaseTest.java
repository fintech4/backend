package com.Toou.Toou.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.Toou.Toou.domain.model.AccountAsset;
import com.Toou.Toou.domain.model.HoldingIndividualStock;
import com.Toou.Toou.port.out.AccountAssetPort;
import com.Toou.Toou.port.out.HoldingStockPort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AccountHoldingUseCaseTest {
    private final AccountAssetPort accountAssetPort = mock(AccountAssetPort.class);
    private final HoldingStockPort holdingStockPort = mock(HoldingStockPort.class);
    private final AccountHoldingUseCase accountHoldingUseCase = new AccountHoldingService(accountAssetPort, holdingStockPort);

    @Test
    void execute() {
        // Given
        String kakaoId = "어떤 아이디 뭔들";
        AccountAsset accountAsset = new AccountAsset();
        accountAsset.setId(1L);

        HoldingIndividualStock stock1 = new HoldingIndividualStock();
        HoldingIndividualStock stock2 = new HoldingIndividualStock();
        List<HoldingIndividualStock> holdings = Arrays.asList(stock1, stock2);

        when(accountAssetPort.findAssetByKakaoId(kakaoId)).thenReturn(accountAsset);
        when(holdingStockPort.findAllHoldingsByAccountAssetId(accountAsset.getId())).thenReturn(holdings);

        AccountHoldingUseCase.Input input = new AccountHoldingUseCase.Input(kakaoId);

        // When
        AccountHoldingUseCase.Output output = accountHoldingUseCase.execute(input);

        // Then
        assertNotNull(output);
        assertEquals(holdings.size(), output.getHoldings().size());
        assertTrue(output.getHoldings().containsAll(holdings));
        verify(accountAssetPort, times(1)).findAssetByKakaoId(kakaoId);
        verify(holdingStockPort, times(1)).findAllHoldingsByAccountAssetId(accountAsset.getId());
    }
}
