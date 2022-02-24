package de.jannisaziz.backend.security;

import de.jannisaziz.backend.security.email.EmailService;
import de.jannisaziz.backend.user.UserService;
import de.jannisaziz.backend.user.User;
import de.jannisaziz.backend.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class LoginService {

    private final AuthenticationManager authManager;
    private final JWTUtilService jwtUtilService;
    private final EmailService emailService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest request) throws IllegalStateException {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = userService.findUser(request.getUsername(), request.getEmail());

            return jwtUtilService.createToken(user);
        } catch (AuthenticationException e) {
            throw new IllegalStateException("Invalid credentials or user not found");
        }
    }

    public String signUp(LoginRequest request) throws IllegalStateException {
        try {
            String confirmationToken = UUID.randomUUID().toString();

            User user = userService.createUser(new User(
                    request.getUsername(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    UserRole.USER,
                    confirmationToken)
            );

            emailService.sendEmail(request.getEmail(), request.getUsername(), confirmationToken);

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            return jwtUtilService.createToken(user);
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
