package com.example.nzgeneration.domain.banner;

import com.example.nzgeneration.domain.banner.dto.BannerResponseDto.BannerResponse;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "배너", description = "배너 관련 API")
public class BannerController {


    private final BannerService bannerService;

    @GetMapping("/api/banner")
    @Operation(
        summary = "배너 단일 조회"
    )
    public ApiResponse<BannerResponse> getBanner() {
        return ApiResponse.onSuccess(bannerService.getBanner());
    }

}
