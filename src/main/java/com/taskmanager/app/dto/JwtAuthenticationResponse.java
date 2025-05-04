package com.taskmanager.app.dto;

public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
