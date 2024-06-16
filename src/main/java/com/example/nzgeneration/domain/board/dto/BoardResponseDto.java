package com.example.nzgeneration.domain.board.dto;

import com.example.nzgeneration.domain.nft.Nft;
import com.example.nzgeneration.domain.nft.dto.NftResponseDto.MyNftResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NftBoardResponse{
        private String nftUrl;
        private Long nftId;


        public static NftBoardResponse toDto(Nft nft){
            return NftBoardResponse.builder()
                .nftUrl(nft.getImageUrl())
                .nftId(nft.getId())
                .build();
        }
    }

}
