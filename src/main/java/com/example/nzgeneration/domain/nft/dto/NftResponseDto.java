package com.example.nzgeneration.domain.nft.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NftResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyNftResponse{
        private List<String> nftImgUrl;

        public static MyNftResponse toDto(List<String> nftImgUrl){
            return MyNftResponse.builder()
                .nftImgUrl(nftImgUrl)
                .build();
        }
    }

}
