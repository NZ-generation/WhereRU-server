package com.example.nzgeneration.domain.retool;

import com.example.nzgeneration.domain.retool.dto.GetReportCountResponse;
import com.example.nzgeneration.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}