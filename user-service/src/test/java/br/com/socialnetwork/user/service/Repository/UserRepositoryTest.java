package br.com.socialnetwork.user.service.Repository;

import br.com.socialnetwork.common.entity.User;
import br.com.socialnetwork.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "br.com.socialnetwork.common.entity")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail_whenUserExists() {
        // Arrange (Preparação)
        // Criamos um novo utilizador para o teste
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        entityManager.persistAndFlush(user);
        Optional<User> foundUserOpt = userRepository.findByEmail("test@example.com");

        assertThat(foundUserOpt).isPresent();
        assertThat(foundUserOpt.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void testFindByEmail_whenUserDoesNotExist() {
        Optional<User> foundUserOpt = userRepository.findByEmail("nonexistent@example.com");
        assertThat(foundUserOpt).isNotPresent();
    }
}
