package com.example.nzgeneration.domain.trashcan.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

public class TrashcanResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTrashcanResponse {
        private Long id;
        private String trashCategory;
        private String imageUrl;
        private Point trashcanPoint;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTrashcanResponses {
        List<GetTrashcanResponse> trashcans;
    }
}