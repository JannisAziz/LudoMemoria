package de.jannisaziz.backend.security;

import de.jannisaziz.backend.user.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("resendConfirmation={email}")
    public String resendConfirmationEmail(@PathVariable String email) throws ResponseStatusException {
        try {
            return loginService.resendConfirmationEmail(email);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("confirm={confirmationToken}")
    public String confirm(HttpServletResponse response, @PathVariable String confirmationToken) throws ResponseStatusException {

        response.setHeader("Location", "http://localhost:3000/");
        response.setStatus(302);

        try {
            return loginService.confirmToken(confirmationToken);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
