package de.jannisaziz.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collection;

public class EmailPasswordAuthToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 560L;
    private final String email;
    private String password;

    public EmailPasswordAuthToken(String email, String password) {
        super(null);
        this.email = email;
        this.password = password;
        this.setAuthenticated(false);
    }

    public EmailPasswordAuthToken(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.password = password;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.password;
    }

    public Object getPrincipal() {
        return this.email;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.password = null;
    }
}
