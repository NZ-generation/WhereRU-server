package com.example.nzgeneration.domain.auth.dto;

import com.example.nzgeneration.domain.auth.enums.ResponseType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginSimpleInfo{
        private String accessToken;
        private String refreshToken;
        private ResponseType responseType;

        public static LoginSimpleInfo toDTO(String accessToken, String refreshToken, ResponseType responseType) {
            return LoginSimpleInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .responseType(responseType)
                .build();

        }

    }
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record OIDCPublicKeysResponse(List<OIDCPublicKey> keys) {
    }
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record OIDCPublicKey(
       String kty,
       String kid,
       String use,
       String alg,
       String n,
       String e
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record OIDCDecodePayload(
        String issuer,
        String audience,
        String subject,
        String email

    ){
    }




}
