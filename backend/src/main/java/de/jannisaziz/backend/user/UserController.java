package de.jannisaziz.backend.user;

import de.jannisaziz.backend.security.JWTUtilService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final JWTUtilService jwtUtilService;

    private boolean isAuthorized(UserRole requiredRole) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> requiredRole.equals(UserRole.valueOf(grantedAuthority.getAuthority())));
    }

    @GetMapping("/currentUser")
    public UserDTO retrieveCurrentUser(HttpServletRequest request) throws ResponseStatusException {
        try {
            String token = request.getHeader("Authorization").replace("Bearer", "").trim();
            String username = jwtUtilService.extractUsername(token);
            return service.findUser(username, null).asDTO();
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping("/id={userId}")
    public UserDTO getUserById(@PathVariable String userId) throws ResponseStatusException {
        try {
            return service.getUserById(userId).asDTO();
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/name={username}")
    public UserDTO getUserByUsername(@PathVariable String username) throws ResponseStatusException {
        try {
            return service.findUser(username, null).asDTO();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping
    public UserDTO updateUser(@RequestBody UserDTO userDTO) throws ResponseStatusException {
        if (isAuthorized(UserRole.USER)) try {
            return service.updateUser(userDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
