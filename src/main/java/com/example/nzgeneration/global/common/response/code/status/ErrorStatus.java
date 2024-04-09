package com.example.nzgeneration.global.common.response.code.status;


import com.example.nzgeneration.global.common.response.code.BaseErrorCode;
import com.example.nzgeneration.global.common.response.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    //일반 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, 403, "금지된 요청입니다."),

    //멤버 관련
    _EMPTY_MEMBER(HttpStatus.CONFLICT, 4001, "존재하지 않는 사용자입니다."),

    //인증 관련
    _EMPTY_JWT(HttpStatus.UNAUTHORIZED, 4011, "JWT가 존재하지 않습니다."),
    _INVALID_JWT(HttpStatus.UNAUTHORIZED, 4012, "유효하지 않은 JWT입니다."),

    //S3 관련
    _FAULT_S3_KEY(HttpStatus.NOT_FOUND, 4021, "잘못된 S3 정보입니다."),

    //파일 업로드 관련
    _FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 4031, "파일 업로드에 실패했습니다.")

    ;


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .httpStatus(httpStatus)
            .build();
    }
}
