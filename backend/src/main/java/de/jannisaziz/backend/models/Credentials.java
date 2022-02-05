package de.jannisaziz.backend.models;

public class Credentials {

    private String email;
    private String password;

    public Credentials(String email, String passwordRaw) {
        this.email = email;
        this.password = passwordRaw;
        //this.password = PasswordEncoder.encode(passwordRaw);
    }
}
