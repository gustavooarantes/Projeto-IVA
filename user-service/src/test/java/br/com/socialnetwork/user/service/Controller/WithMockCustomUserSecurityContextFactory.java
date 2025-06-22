package br.com.socialnetwork.user.service.Controller;

import br.com.socialnetwork.common.entity.User;
import br.com.socialnetwork.user.service.Controller.WithMockCustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User principal = new User();
        principal.setId(customUser.id());
        principal.setUsername(customUser.username());
        principal.setPassword("password");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}