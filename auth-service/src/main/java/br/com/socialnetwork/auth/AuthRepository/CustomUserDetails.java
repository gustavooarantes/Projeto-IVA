package br.com.socialnetwork.auth.AuthRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetails extends UserDetailsService {
    UserDetails loadUserById(Long id) throws UsernameNotFoundException;
}
