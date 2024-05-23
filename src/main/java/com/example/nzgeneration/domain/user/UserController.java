package com.example.nzgeneration.domain.user;


import com.example.nzgeneration.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("member/stamp")
    public ApiResponse<String> addStamp() {
        userService.addStamp();
        return ApiResponse.onSuccess("스탬프 찍기 성공");
    }

}
