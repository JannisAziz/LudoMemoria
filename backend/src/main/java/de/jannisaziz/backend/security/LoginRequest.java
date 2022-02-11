package de.jannisaziz.backend.security;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
        @Nullable private final String username;
        @Nullable private final String email;
        private final String password;
}