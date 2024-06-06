package com.example.nzgeneration.domain.banner;

import com.example.nzgeneration.domain.banner.dto.BannerResponseDto.BannerResponse;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerResponse getBanner() {
        return bannerRepository.findBanner()
            .map(banner -> new BannerResponse(banner.getTitle(), banner.getSubTitle(), banner.getBannerUrl()))
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_BANNER));
    }
}
