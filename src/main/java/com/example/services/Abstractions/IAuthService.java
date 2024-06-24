package com.example.services.Abstractions;

import com.example.infrastructure.JwtResponse;
import com.example.infrastructure.LoginRequest;
import com.example.infrastructure.SignupRequest;
import com.example.infrastructure.TwoFactorTokenDto;
import org.springframework.stereotype.Repository;

import javax.security.auth.message.AuthException;

@Repository
public interface IAuthService {
    JwtResponse login(LoginRequest request);
    JwtResponse signup(SignupRequest request) throws AuthException;
    JwtResponse refresh(String request) throws AuthException;
    void revokeToken(String refreshToken);
    boolean confirmEmail(String token);
    JwtResponse login2FA(TwoFactorTokenDto request);
}
