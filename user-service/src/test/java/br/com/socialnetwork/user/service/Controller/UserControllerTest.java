package br.com.socialnetwork.user.service.Controller;

import br.com.socialnetwork.common.entity.User;
import br.com.socialnetwork.user.controller.UserController;
import br.com.socialnetwork.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testGetUserById() throws Exception {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        given(userService.findById(userId)).willReturn(user);

        // Act & Assert
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @WithMockCustomUser(id = 1L)
    void testUpdateUser_whenAuthorized() throws Exception {
        // Arrange
        Long userId = 1L; // O ID na URL é o mesmo do utilizador logado
        User userDetails = new User();
        userDetails.setFullName("Nome Atualizado");

        given(userService.updateUser(eq(userId), any(User.class))).willReturn(userDetails);

        // Act & Assert
        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetails))
                        .with(csrf())) // Não esquecer o CSRF para PUT/POST/DELETE
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Nome Atualizado"));
    }

    @Test
    @WithMockCustomUser(id = 2L)
    void testUpdateUser_whenNotAuthorized() throws Exception {
        // Arrange
        Long targetUserId = 1L;
        User userDetails = new User();
        userDetails.setFullName("Nome Atualizado");

        mockMvc.perform(put("/api/users/{id}", targetUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetails))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}