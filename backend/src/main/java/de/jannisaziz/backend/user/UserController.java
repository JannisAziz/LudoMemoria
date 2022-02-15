package de.jannisaziz.backend.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @GetMapping("/id/{userId}")
    public User getUserById(@PathVariable String userId) throws ResponseStatusException {
        try {
            return service.getUserById(userId);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/name/{username}")
    public User getUserByUsername(@PathVariable String username) throws ResponseStatusException {
        try {
            return service.findUser(username, null);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/update")
    public String updateUser(@RequestBody UserDTO userDTO) throws ResponseStatusException {
        try {
            return service.updateUser(userDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
