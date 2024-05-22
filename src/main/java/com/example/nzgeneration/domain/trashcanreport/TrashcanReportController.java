package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.trashcanreport.dto.TrashcanReportRequestDto.AddTrashcanReportRequest;
import com.example.nzgeneration.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrashcanReportController {

    private final TrashcanReportService trashcanReportService;

    @PostMapping("trashcan")
    public ApiResponse<String> addTrashcanReport(
        @RequestBody AddTrashcanReportRequest addTrashcanReportRequest) {
        trashcanReportService.addTrashcanReport(addTrashcanReportRequest.getMapX(),
            addTrashcanReportRequest.getMapY(), addTrashcanReportRequest.getImageUrl(),
            addTrashcanReportRequest.getTrashCategory());
        return ApiResponse.onSuccess("제보에 성공했습니다.");
    }
}