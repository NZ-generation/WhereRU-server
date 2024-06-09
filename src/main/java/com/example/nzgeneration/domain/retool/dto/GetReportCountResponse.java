package com.example.nzgeneration.domain.retool.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetReportCountResponse {

    private int reportCount;
    private int errorCount;

    private List<Integer> reportCountList;
    private List<Integer> errorCountList;
    private List<String> month;
}