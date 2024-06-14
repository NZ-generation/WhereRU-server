package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.nft.dto.NftResponseDto.MyNftResponse;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nft")
@Tag(name="Nft", description = "Nft 관련")
public class NftController {
    public static NftService nftService;

    @GetMapping("/my-nft")
    @Operation(summary = "나의 Nft 조회")
    public ApiResponse<MyNftResponse> getMyNft(@CurrentUser User user){
        return ApiResponse.onSuccess(nftService.getMyNft(user));
    }



}
