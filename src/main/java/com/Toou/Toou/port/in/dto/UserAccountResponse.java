package com.Toou.Toou.port.in.dto;

import com.Toou.Toou.domain.model.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserAccountResponse {

	private Long id;

	@Schema(description = "카카오 아이디")
	private String kakaoId;

	@Schema(description = "가입한 이메일")
	private String email;

	@Schema(description = "유저 이름")
	private String username;

	@Schema(description = "프로필 이미지 url")
	private String profileImageUrl;

	public static UserAccountResponse fromDomainModel(UserAccount domainModel) {
		return new UserAccountResponse(
				domainModel.getId(),
				domainModel.getKakaoId(),
				domainModel.getEmail(),
				domainModel.getUsername(),
				domainModel.getProfileImageUrl()
		);
	}
}
