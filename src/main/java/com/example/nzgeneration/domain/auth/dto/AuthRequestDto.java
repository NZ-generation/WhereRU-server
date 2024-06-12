package com.example.nzgeneration.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private String authCode;

    }


    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenRevokeRequest{
        private String client_id;
        private String client_secret;
        private String token;
        private String token_type_hint;

    }




}
