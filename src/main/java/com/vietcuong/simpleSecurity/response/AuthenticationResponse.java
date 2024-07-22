package com.vietcuong.simpleSecurity.response;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AuthenticationResponse {


    private String token;
    private String message;
    private String expirationTime;


    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public AuthenticationResponse(String token, String message, String expirationTime) {
        this.expirationTime = expirationTime;
        this.message = message;
        this.token = token;
    }


}
