package de.jannisaziz.backend.models;

import java.util.List;

public class User {

    String userId;
    String email;
    String password;

    List<String> oauthConnections;

    UserData userData;
}
