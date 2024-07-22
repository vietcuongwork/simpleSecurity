package com.vietcuong.simpleSecurity.service;


import com.vietcuong.simpleSecurity.entity.Token;
import com.vietcuong.simpleSecurity.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<Token> getAllToken() {
        return tokenRepository.findAll();
    }

    public List<String> allTokensToString() {
        List<String> finalList = new ArrayList<>();
        List<Token> tokenList = tokenRepository.findAll();
        for (Token token : tokenList) {
            finalList.add(token.toString());
        }
        return finalList;
    }
}
