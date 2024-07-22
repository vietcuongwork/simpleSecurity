package com.vietcuong.simpleSecurity.service;


import com.vietcuong.simpleSecurity.config.JwtService;
import com.vietcuong.simpleSecurity.entity.Token;
import com.vietcuong.simpleSecurity.entity.User;
import com.vietcuong.simpleSecurity.repository.TokenRepository;
import com.vietcuong.simpleSecurity.repository.UserRepository;
import com.vietcuong.simpleSecurity.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat(
            "HH:mm:ss");

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public AuthenticationResponse register(User userRequest) {
        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setRole(userRequest.getRole());
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        saveUserToken(token, user);
        String formattedDate = dateFormat.format(
                jwtService.extractExpirationTime(token));
        String formattedTime = timeFormat.format(
                jwtService.extractExpirationTime(token));

        return new AuthenticationResponse(token, "Registered successfully",
                                          formattedDate + " " + formattedTime);
    }

    public AuthenticationResponse login(User userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(), userRequest.getPassword()));
        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(token, user);
        String formattedDate = dateFormat.format(
                jwtService.extractExpirationTime(token));
        String formattedTime = timeFormat.format(
                jwtService.extractExpirationTime(token));
        return new AuthenticationResponse(token, "Logged in successfully",
                                          formattedDate + " " + formattedTime);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokenByUser(
                user.getId());
        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(t -> {
                t.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(validTokenListByUser);
    }

    private void saveUserToken(String token, User user) {
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setLoggedOut(false);
        tokenEntity.setUser(user);
        tokenRepository.save(tokenEntity);
    }

}
