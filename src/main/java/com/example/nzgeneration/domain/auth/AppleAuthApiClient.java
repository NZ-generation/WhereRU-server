package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.OIDCPublicKeysResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "AppleOAuthClient", url = "https://appleid.apple.com")
public interface AppleAuthApiClient {

    @GetMapping("/auth/keys")
    OIDCPublicKeysResponse getAppleOIDCOpenKeys();


}
