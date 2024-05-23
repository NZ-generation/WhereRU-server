package com.example.nzgeneration.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserRequest {
        private String nickName;
        private String walletAddress;
        private String profileImgUrl;
        private boolean isAllowLocationInfo;
        private boolean isAllowAdInfo;

    }

}
