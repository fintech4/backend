package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.AccountAsset;

public interface AccountAssetPort {

	AccountAsset findByProviderId(String providerId);

	AccountAsset saveAsset(AccountAsset accountAsset);
}
