package com.example.nzgeneration.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

@Configuration
public class Web3jConfig {

    @Value("${web3j.client-address}")
    private String clientAddress;

    @Value("${web3j.credentials-private-key}")
    private String privateKey;

    @Value("${web3j.chain-id}")
    private long chainId;


    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(clientAddress));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }

    @Bean(name = "web3jTransactionManager")
    public TransactionManager transactionManager(Web3j web3j, Credentials credentials) {
        return new RawTransactionManager(web3j, credentials, chainId);
    }

}
