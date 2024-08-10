package com.Toou.Toou.port.out;

import com.Toou.Toou.domain.model.AccountAsset;

public interface AccountAssetPort {

	AccountAsset findAssetByKakaoId(String kakaoId);

	AccountAsset saveAsset(AccountAsset accountAsset);
}
