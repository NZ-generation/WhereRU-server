package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.nft.dto.NftResponseDto.MyNftResponse;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.openai.OpenAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NftService {

    private final NftRepository nftRepository;
    private final OpenAIService openAIService;

    public List<MyNftResponse> getMyNfts(User user) {
        List<Nft> nftList = nftRepository.findByUser(user);
        return nftList.stream()
            .map(MyNftResponse::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public MyNftResponse makeMyNft(User user, String gender, String action, String animal)
        throws JsonProcessingException {
        String result = openAIService.createImage(openAIService.buildPrompt(gender, action, animal));
        Nft nft = Nft.toEntity(user, result);
        nftRepository.save(nft);
        user.updateNftCount();
        return MyNftResponse.toDto(nft);
    }

}
