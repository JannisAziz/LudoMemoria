package de.jannisaziz.backend.services;

import de.jannisaziz.backend.models.User;
import de.jannisaziz.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String userId) throws IllegalArgumentException {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User " + userId + " not found!"));
    }
}
