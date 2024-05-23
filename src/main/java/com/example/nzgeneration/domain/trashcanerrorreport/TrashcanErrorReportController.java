package com.example.nzgeneration.domain.trashcanerrorreport;

import com.example.nzgeneration.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrashcanErrorReportController {

    private final TrashcanErrorReportService trashcanErrorReportService;

    @PostMapping("trashcan-error/{trashcan-id}")
    public ApiResponse<String> addError(@PathVariable(value = "trashcan-id") Long id) {
        return ApiResponse.onSuccess(trashcanErrorReportService.addError(id));
    }
}