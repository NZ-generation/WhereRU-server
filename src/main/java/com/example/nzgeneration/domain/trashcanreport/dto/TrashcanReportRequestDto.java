package com.example.nzgeneration.domain.trashcanreport.dto;

import com.example.nzgeneration.domain.trashcan.TrashCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TrashcanReportRequestDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class AddTrashcanReportRequest {
        int mapX;
        int mapY;
        String imageUrl;
        TrashCategory trashCategory;
    }

}