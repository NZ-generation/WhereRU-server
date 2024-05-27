package com.example.nzgeneration.domain.auth;

import com.example.nzgeneration.domain.auth.dto.AuthRequestDto.CreateUserRequest;
import com.example.nzgeneration.domain.auth.dto.AuthResponseDto.LoginSimpleInfo;
import com.example.nzgeneration.domain.auth.enums.ResponseType;
import com.example.nzgeneration.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name="Auth", description = "인증(로그인, 회원가입) 관련")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인/회원가입 api", description = "code : Authorization code / 회원가입, 로그인 구분 없이 동일한 API 사용")
    public ApiResponse<LoginSimpleInfo> login(@RequestParam String idToken){
        LoginSimpleInfo loginSimpleInfo = authService.login(idToken);
        if(loginSimpleInfo.getResponseType()== ResponseType.SIGN_IN){
            return ApiResponse.onSuccess(loginSimpleInfo);
        }
        return ApiResponse.onFailure(4003, "추가 정보 입력이 필요합니다", loginSimpleInfo);
    }

    @PostMapping("/signup/extra")
    @Operation(summary = "회원가입 추가 정보 입력 api")
    public ApiResponse<LoginSimpleInfo> signUp(@RequestParam String token, @RequestBody CreateUserRequest createUserRequest){
        return ApiResponse.onSuccess(authService.signUp(token, createUserRequest));
    }


}