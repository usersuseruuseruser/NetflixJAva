package com.example.Controllers;

import com.example.infrastructure.*;
import com.example.services.Abstractions.IAuthService;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Refreshable;
import javax.security.auth.message.AuthException;

@RequestMapping("auth")
@RestController
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signin")
    public ResponseEntity login(@RequestBody LoginRequest jwtRequest) {
        JwtResponse jwtResponse = authService.login(jwtRequest);

        if (jwtResponse == null) {
            return ResponseEntity.accepted().body("2FA");
        }

        if (jwtResponse.refreshToken != null){
            ResponseCookie cookie = ResponseCookie.from("refresh-token", jwtResponse.refreshToken)
                    .httpOnly(true)
                    .path("/")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(jwtResponse);
        }

        return ResponseEntity.ok(jwtResponse.accessToken);
    }

    @PostMapping("signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupRequest jwtRequest) throws AuthException {
        return ResponseEntity.ok(authService.signup(jwtRequest));
    }

    @PostMapping("refresh-token")
    public ResponseEntity refresh(@CookieValue("refresh-token") String refreshToken) throws AuthException {
        if (refreshToken == null) {
            return ResponseEntity.status(401).body("Refresh token is not found");
        }

        JwtResponse jwtResponse = authService.refresh(refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refresh-token", jwtResponse.refreshToken)
                .httpOnly(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(jwtResponse.accessToken);
    }

    @PostMapping("revoke-token")
    public ResponseEntity revokeToken(@CookieValue("refresh-token") String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).body("Refresh token is not found");
        }

        authService.revokeToken(refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refresh-token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(null);
    }

    @GetMapping("confirm-email")
    public ResponseEntity confirmEmail(String token) {
        authService.confirmEmail(token);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("send-2fa")
    public ResponseEntity SendTwoFactorToken(@RequestBody TwoFactorTokenDto jwtRequest){
        JwtResponse jwtResponse = authService.login2FA(jwtRequest);

        if (jwtResponse.refreshToken != null){
            ResponseCookie cookie = ResponseCookie.from("refresh-token", jwtResponse.refreshToken)
                    .httpOnly(true)
                    .path("/")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(jwtResponse.accessToken);
        }

        return ResponseEntity.ok(jwtResponse.accessToken);
    }

}
