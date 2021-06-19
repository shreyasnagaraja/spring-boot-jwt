package com.example.demo;

public class AuthenticationResponse {
    private final String s;

    public AuthenticationResponse(String jwt) {
        this.s = jwt;
    }

    public String getJwt() {
        return s;
    }
}
