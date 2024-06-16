package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.nft.dto.NftResponseDto.MyNftResponse;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.openai.OpenAIService;
import com.example.nzgeneration.global.security.CurrentUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nft")
@Tag(name="Nft", description = "Nft 관련")
public class NftController {
    private final NftService nftService;


    @GetMapping("/my-nft")
    @Operation(summary = "나의 Nft 조회")
    public ApiResponse<List<MyNftResponse>> getMyNft(@CurrentUser User user){
        return ApiResponse.onSuccess(nftService.getMyNfts(user));
    }

    @PostMapping("/")
    @Operation(summary = "나의 Nft 생성")
    public ApiResponse<MyNftResponse> makeNft(@CurrentUser User user,@RequestParam String gender, @RequestParam String action,
        @RequestParam String animal) throws JsonProcessingException{
        return ApiResponse.onSuccess(nftService.makeMyNft(user, gender, action, animal));

    }



}
