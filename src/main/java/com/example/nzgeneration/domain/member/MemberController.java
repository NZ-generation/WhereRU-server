package com.example.nzgeneration.domain.member;

import com.example.nzgeneration.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("member/stamp")
    public ApiResponse<String> addStamp() {
        memberService.addStamp();
        return ApiResponse.onSuccess("스탬프 찍기 성공");
    }
}