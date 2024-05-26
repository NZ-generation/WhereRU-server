package com.example.nzgeneration.domain.user;


import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("member/stamp")
    public ApiResponse<String> addStamp(@CurrentUser User user) {
        userService.addStamp(user);
        return ApiResponse.onSuccess("스탬프 찍기 성공");
    }

}
