package com.example.nzgeneration.domain.badge;

import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.BadgeInfo;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "뱃지", description = "뱃지 관련 API")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("user/badge")
    @Operation(
        summary = "내 뱃지 조회",
        description = "내가 획득한 뱃지 조회"
    )
    public ApiResponse<BadgeInfo> getMyBadgeList(@CurrentUser User user) {
        return ApiResponse.onSuccess(badgeService.getMyBadgeList(user));
    }


}