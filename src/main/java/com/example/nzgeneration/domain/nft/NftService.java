package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.nft.dto.NftResponseDto.MyNftResponse;
import com.example.nzgeneration.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NftService {

    public final NftRepository nftRepository;

    public MyNftResponse getMyNft(User user){
        List<Nft> nftList = nftRepository.findByUser(user);
        List<String> nftImgUrlList = new ArrayList<>();
        for(Nft nft : nftList){
            nftImgUrlList.add(nft.getImageUrl());
        }
        return MyNftResponse.toDto(nftImgUrlList);
    }

}
