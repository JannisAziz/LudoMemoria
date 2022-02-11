package de.jannisaziz.backend.security;

import de.jannisaziz.backend.user.UserService;
import de.jannisaziz.backend.user.User;
import de.jannisaziz.backend.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class LoginService {

    private final UserService userService;
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public User signIn(LoginRequest request) throws IllegalStateException, InvalidParameterException {
        try {
            User user = userService.findUser(request.getUsername(), request.getEmail());
            String encodedRequestPassword = argon2PasswordEncoder.encode(request.getPassword());

            if (user.getPassword().equals(encodedRequestPassword))
                return user;
            else {
                throw new InvalidParameterException("Wrong Password");
            }
        } catch (UsernameNotFoundException | InvalidParameterException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public String signUp(LoginRequest request) throws IllegalStateException {
        try {
            String confirmationToken = UUID.randomUUID().toString();

            return userService.createUser(new User(
                    request.getUsername(),
                    request.getEmail(),
                    argon2PasswordEncoder.encode(request.getPassword()),
                    UserRole.USER,
                    confirmationToken)
            );
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
