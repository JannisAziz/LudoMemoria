package de.jannisaziz.backend.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    public User(String username, String email, String password, UserRole userRole, String confirmationToken) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.confirmationToken = confirmationToken;
    }

    private String username;
    private String email;
    private String password;
    private UserRole userRole;

    private String confirmationToken;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Transient
    public UserDTO asDTO() {
        return UserDTO.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .build();
    }

    @Transient
    public void updateFromDTO(UserDTO userDTO) {
        this.id = userDTO.getId(); // Should never actually change
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
    }
}
