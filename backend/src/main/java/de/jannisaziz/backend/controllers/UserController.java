package de.jannisaziz.backend.controllers;

import de.jannisaziz.backend.models.Credentials;
import de.jannisaziz.backend.models.User;
import de.jannisaziz.backend.models.UserData;
import org.springframework.web.bind.annotation.*;

@RestController("user")
public class UserController {

    @GetMapping("{userId}")
    public String getUser(@PathVariable String userId) {
        return null;
    }

    @PutMapping
    public String addUser(@RequestBody User user) {
        return null;
    }

    @DeleteMapping("{userId}")
    public String deleteUser(@PathVariable String userId) {
        return null;
    }

    @PatchMapping
    public String updateUser(@RequestBody User user) {
        return null;
    }

    @PatchMapping("data")
    public String updateUserData(@RequestBody UserData userData) {
        return null;
    }

    @PatchMapping("password")
    public String updatePassword(@RequestBody Credentials credentials) {
        return null;
    }
}
