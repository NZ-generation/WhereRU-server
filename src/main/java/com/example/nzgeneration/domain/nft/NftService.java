package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.nft.dto.NftResponseDto.MyNftResponse;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.contracts.NFT;
import com.example.nzgeneration.global.openai.OpenAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
@RequiredArgsConstructor
public class NftService {

    private final NftRepository nftRepository;
    private final OpenAIService openAIService;
    private NFT nft;
    private final Credentials credentials;
    private final Web3j web3j;
    private final TransactionManager web3jTransactionManager;


    @PostConstruct
    public void init() {
        try {
            deployContract();
        } catch (Exception e) {
            e.printStackTrace();  // 또는 적절한 로깅 처리
        }
    }
    public List<MyNftResponse> getMyNfts(User user) {
        List<Nft> nftList = nftRepository.findByUser(user);
        return nftList.stream()
            .map(MyNftResponse::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public MyNftResponse makeMyNft(User user, String gender, String action, String animal)
        throws Exception {
        String result = openAIService.createImage(openAIService.buildPrompt(gender, action, animal));
        Nft nft = Nft.toEntity(user, result);
        nftRepository.save(nft);
        user.updateNftCount();
        mintNFT(user.getWalletAddress(), BigInteger.valueOf(nft.getId()), result);
        return MyNftResponse.toDto(nft);
    }



    private void deployContract() throws Exception {
        ContractGasProvider gasProvider = new DefaultGasProvider();
        nft = NFT.deploy(web3j, web3jTransactionManager, gasProvider, "MyNFT", "MNFT").send();
    }

    public void mintNFT(String toAddress, BigInteger tokenId, String tokenURI) throws Exception {
        if (nft == null) {
            throw new IllegalStateException("NFT contract is not deployed.");
        }
        TransactionReceipt transactionReceipt = nft.mint(toAddress, tokenId, tokenURI).send();
        System.out.println("NFT Minted: " + transactionReceipt.getTransactionHash());
    }

}
