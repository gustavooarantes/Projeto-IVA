package br.com.socialnetwork.user.service.Service;

import br.com.socialnetwork.common.entity.User;
import br.com.socialnetwork.user.repository.UserRepository;
import br.com.socialnetwork.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 1. Habilita a integração do Mockito com o JUnit 5
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // 2. Cria um mock (um "impostor") para o UserRepository.
    //    Não vamos usar a base de dados real.
    @Mock
    private UserRepository userRepository;

    // 3. Cria uma instância real do UserService, mas injeta os mocks (@Mock)
    //    declarados nesta classe dentro dele.
    @InjectMocks
    private UserService userService;

    @Test
    void testFindById_whenUserExists() {
        // Arrange (Preparação)
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        // Dizemos ao mock para retornar o nosso 'user' quando 'findById' for chamado com o ID 1L
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act (Ação)
        User foundUser = userService.findById(userId);

        // Assert (Verificação)
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("testuser", foundUser.getUsername());

        // Verifica se o método findById do repositório foi chamado exatamente 1 vez
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_whenUserNotFound() {
        // Arrange
        Long userId = 1L;
        // Dizemos ao mock para retornar um Optional vazio, simulando um utilizador não encontrado
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        // Verificamos se uma EntityNotFoundException é lançada quando chamamos o método
        assertThrows(EntityNotFoundException.class, () -> {
            userService.findById(userId);
        });

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFullName("Nome Antigo");

        User userDetails = new User();
        userDetails.setFullName("Nome Novo");
        userDetails.setBio("Nova bio");

        // Simulamos o 'findById' e o 'save'
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User updatedUser = userService.updateUser(userId, userDetails);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("Nome Novo", updatedUser.getFullName());
        assertEquals("Nova bio", updatedUser.getBio());

        // Verificamos a ordem e as chamadas
        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
    }
}