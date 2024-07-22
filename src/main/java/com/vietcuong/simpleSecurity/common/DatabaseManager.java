package com.vietcuong.simpleSecurity.common;

import com.vietcuong.simpleSecurity.repository.TokenRepository;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager {

    private final TokenRepository tokenRepository;

    @Autowired
    public DatabaseManager(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;

    }

    @PreDestroy
    public void clearTokenTable() {
        System.out.println("Clearing tokens table before shutdown...");
        tokenRepository.deleteAll();
    }

}
