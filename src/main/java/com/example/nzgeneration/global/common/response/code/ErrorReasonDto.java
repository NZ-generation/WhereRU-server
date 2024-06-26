package com.example.nzgeneration.global.common.response.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDto {

    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final int code;
    private final String message;

}
