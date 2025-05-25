package br.com.socialnetwork.user.service;

import br.com.socialnetwork.common.entity.User;
import br.com.socialnetwork.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public List<User> searchUsers(String query) {
        return userRepository.findByUsernameContainingOrFullNameContaining(query, query);
    }

    public User updateUser(Long id, User userDetails) {
        User user = findById(id);
        
        user.setFullName(userDetails.getFullName());
        user.setBio(userDetails.getBio());
        user.setLocation(userDetails.getLocation());
        user.setWebsite(userDetails.getWebsite());
        user.setProfilePictureUrl(userDetails.getProfilePictureUrl());
        
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
} 