package de.jannisaziz.backend.security;

import de.jannisaziz.backend.user.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("signIn")
    public UserDTO signIn(@RequestBody LoginRequest request) throws ResponseStatusException {
        try {
            return loginService.signIn(request).asDTO();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("signUp")
    public String signUp(@RequestBody LoginRequest request) throws ResponseStatusException {
        try {
            return loginService.signUp(request); // Returns "Confirmation email sent" on success
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
