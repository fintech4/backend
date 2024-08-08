package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserAccountResponse {

	private Long id;
	private String kakaoId;
	private String email;
	private String username;
	private String profileImageUrl;

	public static UserAccountResponse fromDomainModel(UserAccount userAccount) {
		return new UserAccountResponse(
				userAccount.getId(),
				userAccount.getKakaoId(),
				userAccount.getEmail(),
				userAccount.getUsername(),
				userAccount.getProfileImageUrl()
		);
	}
}
