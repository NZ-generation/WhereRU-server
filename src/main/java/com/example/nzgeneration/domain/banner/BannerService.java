package com.example.nzgeneration.domain.banner;

import com.example.nzgeneration.domain.banner.dto.BannerResponseDto.BannerResponse;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerResponse getBanner() {
        return bannerRepository.findBanner()
            .map(banner -> {
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy.M.d.(EEE)",
                    Locale.KOREAN);
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M.d.(EEE)",
                    Locale.KOREAN);
                String displayPeriod = banner.getStartDate().format(formatter1) + " - " + banner.getEndDate().format(formatter2);
                return new BannerResponse(banner.getTitle(), banner.getSubTitle(), banner.getBannerUrl(), displayPeriod);
            })
            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_BANNER));
    }
}
