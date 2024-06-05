package com.example.nzgeneration.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserRequest {
        private String token;
        private String nickName;
        private String walletAddress;
        private Boolean isAllowLocationInfo;
        private Boolean isAllowAdInfo;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserIdTokenRequest{
        private String idToken;

    }



}
