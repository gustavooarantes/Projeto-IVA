package br.com.socialnetwork.auth.Security;

import br.com.socialnetwork.auth.security.JwtTokenProvider;
import br.com.socialnetwork.common.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    private final String jwtSecret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final int jwtExpirationInMs = 60000; // 1 minuto para o teste

    @BeforeEach
    void setUp() {
        // Como @Value não funciona fora do contexto Spring, injetamos os valores manualmente
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", jwtExpirationInMs);
    }

    @Test
    void testGenerateTokenAndGetUserIdFromJWT() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        // Act
        String token = jwtTokenProvider.generateToken(authentication);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        // Assert
        assertNotNull(token);
        assertEquals(1L, userId);
    }

    @Test
    void testValidateToken_withValidToken() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        String token = jwtTokenProvider.generateToken(authentication);

        // Act & Assert
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void testValidateToken_withInvalidToken() {
        // Arrange
        String invalidToken = "tokeninvalido";
        String tamperedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.fake_signature";

        // Act & Assert
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
        assertFalse(jwtTokenProvider.validateToken(tamperedToken));
    }

    @Test
    void testValidateToken_withExpiredToken() throws InterruptedException {
        // Arrange
        // Criamos um token com expiração de 1 milissegundo
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", 1);
        User user = new User();
        user.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        String expiredToken = jwtTokenProvider.generateToken(authentication);

        // Esperamos o token expirar
        Thread.sleep(5);

        // Act & Assert
        assertFalse(jwtTokenProvider.validateToken(expiredToken));
    }
}
