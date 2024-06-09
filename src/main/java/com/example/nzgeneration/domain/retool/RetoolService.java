package com.example.nzgeneration.domain.retool;

import com.example.nzgeneration.domain.retool.dto.GetPercentCategoryResponse;
import com.example.nzgeneration.domain.retool.dto.GetReportCountResponse;
import com.example.nzgeneration.domain.trashcan.TrashcanService;
import com.example.nzgeneration.domain.trashcanerrorreport.TrashcanErrorReportService;
import com.example.nzgeneration.domain.trashcanreport.TrashcanReportService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetoolService {


    private final TrashcanReportService trashcanReportService;
    private final TrashcanErrorReportService trashcanErrorReportService;
    private final TrashcanService trashcanService;

    public GetReportCountResponse findReportCount() {
        int reportCount = trashcanReportService.findCountReport();
        int errorCount = trashcanErrorReportService.findCountError();

        List<Integer> reportCountList = trashcanReportService.findReportCountList();
        List<Integer> errorCountList = trashcanErrorReportService.findErrorCountList();

        //최근 6달 리스트
        List<String> month =  new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate currentDate = LocalDate.now();
        for(int i = 0; i < 6; i++) {
            month.add(currentDate.format(formatter));
            currentDate = currentDate.minusMonths(1);
        }

        return GetReportCountResponse.builder()
            .reportCount(reportCount)
            .errorCount(errorCount)
            .reportCountList(reportCountList)
            .errorCountList(errorCountList)
            .month(month)
            .build();
    }

    public GetPercentCategoryResponse findPercentByCategory() {
        return trashcanService.findPercentByCategory();
    }
}