package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCDecodePayload;

@Component
@RequiredArgsConstructor
public class AppleOauthHelper {
    private final AppleAuthApiClient appleAuthApiClient;
    private final OauthOIDCHelper oauthOIDCHelper;

    @Value("${social-login.provider.apple.client-id}")
    private String aud;

    @Value("${social-login.provider.apple.issuer}")
    private String iss;

    public OIDCDecodePayload getOIDCDecodePayload(String token){
        OIDCPublicKeysResponse oidcPublicKeysResponse = appleAuthApiClient.getAppleOIDCOpenKeys();
        return oauthOIDCHelper.getPayloadFromIdToken(
            token, iss, aud, oidcPublicKeysResponse
        );
    }

}
