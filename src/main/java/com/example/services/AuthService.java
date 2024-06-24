package com.example.services;

import com.example.config.MailConfig;
import com.example.exceptions.GodException;
import com.example.filter.JwtProvider;
import com.example.infrastructure.JwtResponse;
import com.example.infrastructure.LoginRequest;
import com.example.infrastructure.SignupRequest;
import com.example.infrastructure.TwoFactorTokenDto;
import com.example.models.RefreshToken;
import com.example.models.Role;
import com.example.models.User;
import com.example.repository.RefreshTokenRepository;
import com.example.repository.UserRepository;
import com.example.services.Abstractions.IAuthService;
import net.bytebuddy.utility.RandomString;
import org.jetbrains.annotations.NotNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.security.auth.message.AuthException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class AuthService implements IAuthService {
    private static final String EMAIL_CONFIRMATION_ENDPOINT = System.getenv("EMAIL_CONFIRMATION_ENDPOINT") == null?
            "http://localhost:8080/auth/confirm-email"
            : System.getenv("EMAIL_CONFIRMATION_ENDPOINT");
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailConfig mailConfig;
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserRepository userRepository, MailConfig mailConfig, JavaMailSender javaMailSender
    , JwtProvider jwtProvider, RefreshTokenRepository refreshTokenRepository) {
        this.mailConfig = mailConfig;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtProvider = jwtProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }
    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new UsernameNotFoundException(request.email));

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new GodException("Invalid password");
        }
        if (!user.enabled){
            throw new GodException("First verify your registration");
        }

        String code = RandomString.make(6);
        sendTwoFactorToken(user.email, code);

        user.verificationCode = code;
        userRepository.save(user);

        return null;

    }

    @Override
    public JwtResponse login2FA(TwoFactorTokenDto request) {
        User user = userRepository.findByVerificationCode(request.token)
                .orElseThrow(() -> new UsernameNotFoundException(request.token));
        user.verificationCode = null;
        userRepository.save(user);

        JwtResponse jwtResponse = getJwtResponse(user);
        if (!request.rememberMe){
            jwtResponse.refreshToken = null;
        }

        return jwtResponse;
    }

    @NotNull
    private JwtResponse getJwtResponse(User user) {
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        RefreshToken refreshToken1 = new RefreshToken();
        refreshToken1.token = refreshToken;
        refreshToken1.user = user;
        refreshTokenRepository.save(refreshToken1);

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse signup(SignupRequest request) throws AuthException {
        if (userRepository.findByEmail(request.email).isPresent()){
            throw new AuthException("User with this email already exists");
        }
        if (userRepository.findByName(request.login).isPresent()){
            throw new AuthException("User with this username already exists");
        }
        if (request.password.length() < 8){
            throw new AuthException("Password is too short");
        }
        if (request.login.length() < 4){
            throw new AuthException("Username is too short");
        }
        User user = new User();
        user.setEmail(request.email);
        user.setName(request.login);
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.USER);
        user.role = roles;
        user.setPassword(passwordEncoder.encode(request.password));

        String verificationCode = RandomString.make(128);
        System.out.println(verificationCode);
        user.verificationCode = verificationCode;
        userRepository.save(user);

        sendVerificationCode(user.email, verificationCode);

        return null;
    }

    @Override
    public JwtResponse refresh(String token) throws AuthException {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);

        if (refreshToken == null){
            throw new AuthException("Invalid refresh token");
        }

        String accessToken = jwtProvider.generateAccessToken(refreshToken.user);
        String newRefreshToken = jwtProvider.generateRefreshToken(refreshToken.user);

        refreshToken.token = newRefreshToken;
        refreshTokenRepository.save(refreshToken);
        return new JwtResponse(accessToken, newRefreshToken);
    }

    @Override
    public void revokeToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Override
    public boolean confirmEmail(String token) {
        Optional<User> user = userRepository.findByVerificationCode(token);
        if (user.isPresent() && user.get().verificationCode.equals(token)){
            user.get().verificationCode = null;
            user.get().enabled = true;
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
    private void sendVerificationCode(String mail, String code) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(mailConfig.getFrom(), mailConfig.getSender());
            helper.setTo(mail);
            helper.setSubject(mailConfig.getSubject());
            String content = getEmailConfirmationMessage(code);
            helper.setText(content);
            System.out.println(content);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    private void sendTwoFactorToken(String mail, String token) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(mailConfig.getFrom(), mailConfig.getSender());
            helper.setTo(mail);
            helper.setSubject(mailConfig.getSubject());
            String content = getTwoFactorTokenMessage(token);
            helper.setText(content);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    private String getEmailConfirmationMessage(String token) {
        System.out.println(token);
        return String.format("To confirm your email, please follow this link: <a href=\"%s?token=%s\">Confirm email</a>",
                EMAIL_CONFIRMATION_ENDPOINT, token);
    }
    private String getTwoFactorTokenMessage(String token){
        return String.format("Your two factor token is: %s", token);
    }
}
