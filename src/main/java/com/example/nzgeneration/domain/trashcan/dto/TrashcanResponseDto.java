package com.example.nzgeneration.domain.trashcan.dto;

import com.example.nzgeneration.domain.trashcan.TrashCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TrashcanResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTrashcanResponse {
        String trashCategory;
        String imageUrl;
    }
}