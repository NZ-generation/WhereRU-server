package com.example.nzgeneration.domain.board;

import com.example.nzgeneration.domain.board.dto.BoardResponseDto.NftBoardResponse;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.common.dto.PageResponseDto;
import com.example.nzgeneration.global.common.response.ApiResponse;
import com.example.nzgeneration.global.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Board", description = "NFT 게시판")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/{id}")
    @Operation(summary = "NFT 둘러보기 게시판 업로드")
    public ApiResponse<String> uploadNft(@CurrentUser User user, @PathVariable("id") Long nftId){
        boardService.uploadNft(user, nftId);
        return ApiResponse.onSuccess("업로드 완료");
    }

    @GetMapping("/latest")
    @Operation(summary = "NFT 둘러보기 게시판 조회")
    public ApiResponse<PageResponseDto<List<NftBoardResponse>>> getLatestNftBoard (@CurrentUser User user, @RequestParam int page, @RequestParam int size){
        return ApiResponse.onSuccess(boardService.getLatestNftBoard(user, page, size));
    }




}
