package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.TokenRevokeRequest;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCPublicKeysResponse;
import com.example.nzgeneration.global.security.JwtOIDCProvider;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCDecodePayload;

@Component
@RequiredArgsConstructor
public class AppleOauthHelper {
    private final AppleAuthApiClient appleAuthApiClient;
    private final OauthOIDCHelper oauthOIDCHelper;
    private final JwtOIDCProvider jwtOIDCProvider;

    @Value("${social-login.provider.apple.client-id}")
    private String aud;

    @Value("${social-login.provider.apple.issuer}")
    private String iss;

    @Value("${social-login.provider.apple.client-id}")
    private String clientId;

    public OIDCDecodePayload getOIDCDecodePayload(String token){
        OIDCPublicKeysResponse oidcPublicKeysResponse = appleAuthApiClient.getAppleOIDCOpenKeys();
        return oauthOIDCHelper.getPayloadFromIdToken(
            token, iss, aud, oidcPublicKeysResponse
        );
    }

    public void revokeToken(String token){
        TokenRevokeRequest tokenRevokeRequest = new TokenRevokeRequest(clientId,
            jwtOIDCProvider.createSecretKey(),token, "refresh_token");
        appleAuthApiClient.revokeAppleRefreshToken(tokenRevokeRequest);
    }

}
