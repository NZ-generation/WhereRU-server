package com.example.nzgeneration.domain.retool;

import com.example.nzgeneration.domain.retool.dto.GetPercentCategoryResponse;
import com.example.nzgeneration.domain.retool.dto.GetReportCountResponse;
import com.example.nzgeneration.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "리툴" ,description = "어드민 페이지 API")
@RequestMapping("/api/retool")
public class RetoolController {

    private final RetoolService retoolService;

    @GetMapping("/report/count")
    public ApiResponse<GetReportCountResponse> findReportCountResponse() {
        return ApiResponse.onSuccess(retoolService.findReportCount());
    }

    @GetMapping("/percent/category")
    public ApiResponse<GetPercentCategoryResponse> findPercentByCategory() {
        return ApiResponse.onSuccess(retoolService.findPercentByCategory());
    }

    @PatchMapping("/report/approve/{reportId}")
    public ApiResponse<String> changeReportStatus(
        @PathVariable Long reportId,
        @RequestParam Boolean isApprove
    ) {
        if(isApprove) retoolService.approveReport(reportId);
        else retoolService.rejectReport(reportId);
        return ApiResponse.onSuccess("승인/거절에 성공했습니다.");
    }

}