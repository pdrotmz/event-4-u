package dev.pdrotmz.event_4_u.repository;

import dev.pdrotmz.event_4_u.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findByUsername(String username);
}
