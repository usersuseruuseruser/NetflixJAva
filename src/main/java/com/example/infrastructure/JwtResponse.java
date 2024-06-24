package com.example.infrastructure;

public class JwtResponse {
    public String accessToken;
    public String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
