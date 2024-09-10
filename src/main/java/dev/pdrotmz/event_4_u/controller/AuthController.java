package dev.pdrotmz.event_4_u.controller;

import dev.pdrotmz.event_4_u.domain.dto.LoginRequestDTO;
import dev.pdrotmz.event_4_u.domain.dto.RegisterRequestDTO;
import dev.pdrotmz.event_4_u.domain.dto.ResponseDTO;
import dev.pdrotmz.event_4_u.domain.model.User;
import dev.pdrotmz.event_4_u.infra.security.TokenService;
import dev.pdrotmz.event_4_u.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        Optional<User> user = this.userRepository.findUserByEmail(registerRequestDTO.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setUsername(registerRequestDTO.username());
            newUser.setEmail(registerRequestDTO.email());
            newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser.getEmail());
            return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = this.userRepository.findUserByEmail(loginRequestDTO.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if(passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user.getEmail());
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
