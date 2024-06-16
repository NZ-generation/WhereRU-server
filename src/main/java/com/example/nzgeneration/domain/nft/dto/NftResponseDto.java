package com.example.nzgeneration.domain.nft.dto;

import com.example.nzgeneration.domain.nft.Nft;
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
        private String nftUrl;
        private Long nftId;

        public static MyNftResponse toDto(Nft nft){
            return MyNftResponse.builder()
                .nftUrl(nft.getImageUrl())
                .nftId(nft.getId())
                .build();
        }
    }

}
