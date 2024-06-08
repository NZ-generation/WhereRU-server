package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.TokenRevokeRequest;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCPublicKeysResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "AppleOAuthClient", url = "https://appleid.apple.com")
public interface AppleAuthApiClient {

    @GetMapping("/auth/keys")
    OIDCPublicKeysResponse getAppleOIDCOpenKeys();

    @PostMapping("/auth/revoke")
    void revokeAppleRefreshToken(@RequestBody TokenRevokeRequest tokenRevokeRequest);





}
