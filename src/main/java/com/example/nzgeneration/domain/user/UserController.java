package com.example.nzgeneration.domain.user;


import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserEditingPageDetailInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserMyPageDetailInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserSigningSimpleInfo;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/stamp")
    public ApiResponse<String> addStamp(@CurrentUser User user) {
        userService.addStamp(user);
        return ApiResponse.onSuccess("스탬프 찍기 성공");
    }
    @GetMapping("/check-nickname/{nickName}")
    @Operation(summary = "닉네임 중복 확인", description = "true : 사용가능한 닉네임, false : 사용 불가능한 닉네임")
    public ApiResponse<Boolean> checkNickName(@PathVariable String nickName){
        boolean status = userService.checkNickNameDuplicate(nickName);
        return ApiResponse.onSuccess(status);
    }
    @PatchMapping("/nickname/{nickName}")
    @Operation(summary = "닉네임 수정")
    public ApiResponse<String> updateNickName(@CurrentUser User user, @PathVariable String nickName){
        userService.updateNickName(user, nickName);
        return ApiResponse.onSuccess("닉네임 수정 완료");

    }
    @PatchMapping("/wallet-address/{walletAddress}")
    @Operation(summary = "지갑주소 수정")
    public ApiResponse<String> updateWalletAddress(@CurrentUser User user, @PathVariable String walletAddress){
        userService.updateWalletAddress(user, walletAddress);
        return ApiResponse.onSuccess("지갑 주소 수정 완료");
    }
    @PatchMapping("/profile-image")
    @Operation(summary = "프로필 사진 수정")
    public ApiResponse<String> updateProfileImg(@CurrentUser User user, @RequestPart MultipartFile image){
        userService.updateUserProfileImage(user, image);
        return ApiResponse.onSuccess("프로필 사진 수정 완료");
    }
    @GetMapping("/days-signing")
    @Operation(summary = "가입 후 며칠이 지났는지(가입일이 1일)")
    public ApiResponse<UserSigningSimpleInfo> getDaysSigning(@CurrentUser User user){
        return ApiResponse.onSuccess(userService.getDaysSigning(user));
    }

    @GetMapping("/my-page")
    @Operation(summary = "마이페이지 조회")
    public ApiResponse<UserMyPageDetailInfo> getMyPageInfo(@CurrentUser User user){
        return ApiResponse.onSuccess(userService.getMyPageInfo(user));
    }

    @GetMapping("/edit-page")
    @Operation(summary = "수정페이지 조회")
    public ApiResponse<UserEditingPageDetailInfo> getEditPage(@CurrentUser User user){
        return ApiResponse.onSuccess(userService.getEditPageInfo(user));
    }




}
