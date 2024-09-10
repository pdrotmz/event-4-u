package dev.pdrotmz.event_4_u.infra.security;

import dev.pdrotmz.event_4_u.domain.model.User;
import dev.pdrotmz.event_4_u.repository.UserRepository;
import dev.pdrotmz.event_4_u.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(UserService userService, TokenService tokenService, UserRepository userRepository) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if(login!=null) {
            User user =  userRepository.findUserByEmail(login)
                    .orElseThrow(() -> new RuntimeException("User not Found"));

            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    public String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader==null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
