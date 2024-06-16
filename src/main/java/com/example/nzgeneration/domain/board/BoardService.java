package com.example.nzgeneration.domain.board;

import com.example.nzgeneration.domain.board.dto.BoardResponseDto.NftBoardResponse;
import com.example.nzgeneration.domain.nft.Nft;
import com.example.nzgeneration.domain.nft.NftRepository;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.common.dto.PageResponseDto;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardRepository boardRepository;
    public final NftRepository nftRepository;



    @Transactional
    public void uploadNft(User user, Long nftId){
        Nft nft = nftRepository.findByUserAndId(user, nftId)
            .orElseThrow(()-> new GeneralException(ErrorStatus._INVALID_NFT));
        Board board = Board.toEntity();
        boardRepository.save(board);
        nft.setBoard(board);

    }

}
