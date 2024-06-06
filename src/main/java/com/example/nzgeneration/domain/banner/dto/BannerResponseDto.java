package com.example.nzgeneration.domain.banner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BannerResponseDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BannerResponse {

        String title;
        String subTitle;
        String bannerUrl;
        String displayPeriod;
    }
}
