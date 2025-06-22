package br.com.socialnetwork.auth.Service;

import br.com.socialnetwork.auth.dto.LoginRequest;
import br.com.socialnetwork.auth.dto.SignupRequest;
import br.com.socialnetwork.auth.dto.TokenResponse;
import br.com.socialnetwork.auth.security.JwtTokenProvider;
import br.com.socialnetwork.auth.service.AuthService;
import br.com.socialnetwork.common.entity.User;
import br.com.socialnetwork.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider tokenProvider;
    @Mock
    private Authentication authentication; // Mock para o objeto de autenticação

    @InjectMocks
    private AuthService authService;

    @Test
    void testSignup_Success() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest("testuser", "test@test.com", "password123", "Test User");
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenProvider.generateToken(authentication)).thenReturn("fake-jwt-token");

        // Act
        TokenResponse tokenResponse = authService.signup(signupRequest);

        // Assert
        assertNotNull(tokenResponse);
        assertEquals("fake-jwt-token", tokenResponse.getAccessToken());
        verify(userRepository).save(any(User.class)); // Verifica se o save foi chamado
    }

    @Test
    void testSignup_UsernameAlreadyExists() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest("testuser", "test@test.com", "password123", "Test User");
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> {
            authService.signup(signupRequest);
        });
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test@test.com", "password123");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenProvider.generateToken(authentication)).thenReturn("fake-jwt-token");

        // Act
        TokenResponse tokenResponse = authService.login(loginRequest);

        // Assert
        assertNotNull(tokenResponse);
        assertEquals("fake-jwt-token", tokenResponse.getAccessToken());
    }
}
