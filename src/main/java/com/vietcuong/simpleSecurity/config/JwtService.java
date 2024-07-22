package com.vietcuong.simpleSecurity.config;


import com.vietcuong.simpleSecurity.entity.User;
import com.vietcuong.simpleSecurity.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "bb89cd30d2a1a8deb78d86d9e442046e9d829df36633379ac855753a52eeed2a";
    private final TokenRepository tokenRepository;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = (byte[]) Decoders.BASE64URL.decode("bb89cd30d2a1a8deb78d86d9e442046e9d829df36633379ac855753a52eeed2a");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder().subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                // Token valid for 24 hours
                .signWith(this.getSecretKey()).compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(this.getSecretKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    private <T> T extractSpecificClaim(String token,
                                       Function<Claims, T> resolver) {
        Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return this.extractSpecificClaim(token, Claims::getSubject);
    }


    public Date extractExpirationTime(String token) {
        return this.extractSpecificClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpirationTime(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
            throws JwtException {
        String username = this.extractUsername(token);
        boolean isUserLoggedOut = tokenRepository.findByToken(token)
                .map(t -> !t.isLoggedOut()).orElse(false);
        if (!isUserLoggedOut) {
            throw new JwtException("User logged out");
        }
        if(isTokenExpired(token)) {
            throw new JwtException("Token expired at: " + extractExpirationTime(token));
        }
        return username.equals(userDetails.getUsername());
    }

}
