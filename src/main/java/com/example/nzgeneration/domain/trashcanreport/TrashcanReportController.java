package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.trashcanreport.dto.TrashcanReportRequestDto.AddTrashcanReportRequest;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TrashcanReportController {

    private final TrashcanReportService trashcanReportService;

    @PostMapping("/api/trashcan")
    public ApiResponse<String> addTrashcanReport(
        @CurrentUser User user,
        @RequestPart MultipartFile image,
        @RequestPart AddTrashcanReportRequest addTrashcanReportRequest) {
        trashcanReportService.addTrashcanReport(
            user,
            image,
            addTrashcanReportRequest.getMapX(),
            addTrashcanReportRequest.getMapY(),
            addTrashcanReportRequest.getTrashCategory());
        return ApiResponse.onSuccess("제보에 성공했습니다.");
    }
}