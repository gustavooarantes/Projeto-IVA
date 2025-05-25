package br.com.socialnetwork.auth.service;

import br.com.socialnetwork.auth.dto.LoginRequest;
import br.com.socialnetwork.auth.dto.SignupRequest;
import br.com.socialnetwork.auth.dto.TokenResponse;
import br.com.socialnetwork.auth.security.JwtTokenProvider;
import br.com.socialnetwork.common.entity.User;
import br.com.socialnetwork.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public TokenResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new EntityExistsException("Nome de usuário já está em uso");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EntityExistsException("Email já está em uso");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setFullName(signupRequest.getFullName());

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                signupRequest.getUsername(),
                signupRequest.getPassword()
            )
        );

        return generateTokenResponse(authentication);
    }

    public TokenResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        return generateTokenResponse(authentication);
    }

    public TokenResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Token inválido");
        }

        Long userId = tokenProvider.getUserIdFromJWT(refreshToken);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            null,
            null
        );

        return generateTokenResponse(authentication);
    }

    private TokenResponse generateTokenResponse(Authentication authentication) {
        String accessToken = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateToken(authentication);
        
        return new TokenResponse(
            accessToken,
            refreshToken,
            "Bearer",
            3600L
        );
    }
} 