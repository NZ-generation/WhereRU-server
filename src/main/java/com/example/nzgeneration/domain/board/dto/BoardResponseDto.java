package com.example.nzgeneration.domain.board.dto;

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
        private List<String> nftImgUrl;

        public static NftBoardResponse toDto(List<String> nftImgUrl){
            return NftBoardResponse.builder()
                .nftImgUrl(nftImgUrl)
                .build();
        }
    }

}
