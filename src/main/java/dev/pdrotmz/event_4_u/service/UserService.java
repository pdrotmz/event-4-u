package dev.pdrotmz.event_4_u.service;

import dev.pdrotmz.event_4_u.domain.User;
import dev.pdrotmz.event_4_u.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    // Create User
    public User createUser(User user) {
        return repository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // Get user By ID
    public Optional<User> getUserById(UUID id) {
        return repository.findById(id);
    }

    // update User info
    public User updateUser(UUID id, User updateUserInfo) {
        return repository.findById(id).map(User -> {
                User.setUsername(updateUserInfo.getUsername());
                User.setEmail(updateUserInfo.getEmail());
                User.setPassword(updateUserInfo.getPassword());
                return repository.save(User);
        }).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // Delete User
    public void deleteUserById(UUID id) {
        repository.deleteById(id);
    }
}
