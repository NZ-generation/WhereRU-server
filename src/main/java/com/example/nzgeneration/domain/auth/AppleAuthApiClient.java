package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.TokenRevokeRequest;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.AppleTokenResponse;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCPublicKeysResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "AppleOAuthClient", url = "https://appleid.apple.com")
public interface AppleAuthApiClient {

    @GetMapping("/auth/keys")
    OIDCPublicKeysResponse getAppleOIDCOpenKeys();

    @PostMapping("/auth/revoke")
    void revokeAppleRefreshToken(@RequestBody TokenRevokeRequest tokenRevokeRequest);

    @PostMapping("/auth/token")
    AppleTokenResponse generateToken(@RequestParam("client_id") String clientId,
        @RequestParam("client_secret") String clientSecret,
        @RequestParam("code") String code,
        @RequestParam("grant_type") String grantType);

}
