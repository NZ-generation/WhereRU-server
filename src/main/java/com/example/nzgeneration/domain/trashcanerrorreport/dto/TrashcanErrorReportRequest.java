package com.example.nzgeneration.domain.trashcanerrorreport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TrashcanErrorReportRequest {


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AddErrorRequest {

        Long trashcanId;
    }
}