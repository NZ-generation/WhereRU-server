package com.example.nzgeneration.domain.trashcanerrorreport;

import com.example.nzgeneration.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "쓰레기통 오류 신고", description = "쓰레기통 오류 신고 API")
public class TrashcanErrorReportController {

    private final TrashcanErrorReportService trashcanErrorReportService;

    @PostMapping("/api/trashcan-error/{trashcan-id}")
    @Operation(
        summary = "쓰레기통 오류 신고"
    )
    public ApiResponse<String> addError(@PathVariable(value = "trashcan-id") Long id) {
        return ApiResponse.onSuccess(trashcanErrorReportService.addError(id));
    }
}