package de.jannisaziz.backend.security;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("signIn")
    public String signIn(@RequestBody LoginRequest request) throws ResponseStatusException {
        try {
            return loginService.login(request);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("signUp")
    public String signUp(@RequestBody LoginRequest request) throws ResponseStatusException {
        try {
            return loginService.signUp(request);
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

        try {
            String locationAddress = "http://"  + InetAddress.getLocalHost().getHostAddress() + ":3000/";

            System.out.println("CONFIRM : " + locationAddress+ " - Token - " + confirmationToken);

            response.setHeader("Location", locationAddress);
            response.setStatus(302);

            return loginService.confirmToken(confirmationToken);
        } catch (IllegalArgumentException | UnknownHostException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
