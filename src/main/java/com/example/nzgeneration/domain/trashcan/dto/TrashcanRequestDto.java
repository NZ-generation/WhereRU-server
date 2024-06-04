package com.example.nzgeneration.domain.trashcan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

public class TrashcanRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTrashcansRequest {
        private Point currentPoint;
        private Point topLeftPoint;
        private Point topRightPoint;
        private Point bottomRightPoint;
        private Point bottomLeftPoint;
    }
}