package com.Toou.Toou.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAccount {

	private Long id;
	private String kakaoId;
	private String email;
	private String username;
	private String profileImageUrl;
}
