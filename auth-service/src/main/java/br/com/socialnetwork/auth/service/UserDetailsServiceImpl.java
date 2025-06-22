package br.com.socialnetwork.auth.service;

import br.com.socialnetwork.auth.AuthRepository.CustomUserDetails;
import br.com.socialnetwork.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements CustomUserDetails {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(username) // ou findByUsername
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado com o email: " + username));
    }

    @Override
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado com o id: " + id));
    }
}
