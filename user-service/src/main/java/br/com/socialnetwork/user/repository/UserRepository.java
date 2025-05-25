package br.com.socialnetwork.user.repository;

import br.com.socialnetwork.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByUsernameContainingOrFullNameContaining(String username, String fullName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 