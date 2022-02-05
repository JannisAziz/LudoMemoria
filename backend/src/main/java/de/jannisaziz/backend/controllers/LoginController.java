package de.jannisaziz.backend.controllers;

import de.jannisaziz.backend.models.Credentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("auth")
public class LoginController {

    @PostMapping("login")
    public String postLogin(@RequestBody Credentials credentials) {
        return null;
    }

    @PostMapping("register")
    public String postRegister(@RequestBody Credentials credentials) {
        return null;
    }

    @PostMapping("signOut")
    public String postSignOut(@RequestBody Credentials credentials) {
        return null;
    }

    @PostMapping("forgotPassword")
    public String postForgotPassword(@RequestBody Credentials credentials) {
        return null;
    }

}
