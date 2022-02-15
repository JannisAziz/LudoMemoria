package de.jannisaziz.backend.security;

import de.jannisaziz.backend.security.email.EmailService;
import de.jannisaziz.backend.user.UserService;
import de.jannisaziz.backend.user.User;
import de.jannisaziz.backend.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class LoginService {

    private final EmailService emailService;
    private final UserService userService;
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public User signIn(LoginRequest request) throws IllegalStateException, IllegalArgumentException {
        try {
            User user = userService.findUser(request.getUsername(), request.getEmail());
            String encodedRequestPassword = argon2PasswordEncoder.encode(request.getPassword());

            if (argon2PasswordEncoder.matches(request.getPassword(), user.getPassword())) return user;
            else throw new IllegalArgumentException("Wrong Password");
        } catch (UsernameNotFoundException | IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public String signUp(LoginRequest request) throws IllegalStateException {
        try {
            String confirmationToken = UUID.randomUUID().toString();

            userService.createUser(new User(
                    request.getUsername(),
                    request.getEmail(),
                    argon2PasswordEncoder.encode(request.getPassword()),
                    UserRole.USER,
                    confirmationToken)
            );

            return emailService.sendEmail(request.getEmail(), request.getUsername(), confirmationToken);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public String resendConfirmationEmail(String toEmail) throws IllegalArgumentException {
        try {
            User user = userService.findUser(null, toEmail);
            return emailService.sendEmail(user.getEmail(), user.getUsername(), user.getConfirmationToken());
        } catch (UsernameNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String confirmToken(String confirmationToken) throws IllegalArgumentException {
        try {
            User user = userService.findUserByConfirmationToken(confirmationToken);
            return userService.confirmUser(user.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
