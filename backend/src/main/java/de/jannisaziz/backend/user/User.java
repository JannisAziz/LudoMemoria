package de.jannisaziz.backend.user;

import de.jannisaziz.backend.game.Game;
import de.jannisaziz.backend.game.Review;
import de.jannisaziz.backend.game.SavedGame;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
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

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    private List<String> oauthConnections = new ArrayList<>();

    private List<SavedGame> savedGames = new ArrayList<>();
    private List<Game> wishlistGames = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    private String description = "New user desc";
    private String birthdate = "10.02.2022";
    private String avatarImg = "avatarImgUrl";
    private String backgroundImg = "bgImgUrl";

    private List<String> socialLinks = new ArrayList<>();

    @Transient
    public UserDTO asDTO() {
        return UserDTO.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .oauthConnections(this.oauthConnections)
                .savedGames(this.savedGames)
                .wishlistGames(this.wishlistGames)
                .reviews(this.reviews)
                .description(this.description)
                .birthdate(this.birthdate)
                .avatarImg(this.avatarImg)
                .backgroundImg(this.backgroundImg)
                .socialLinks(this.socialLinks)
                .build();
    }

    @Transient
    public void updateFromDTO(UserDTO userDTO) {
        this.id = userDTO.getId(); // Should never actually change
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.oauthConnections = userDTO.getOauthConnections();
        this.savedGames = userDTO.getSavedGames();
        this.wishlistGames = userDTO.getWishlistGames();
        this.reviews = userDTO.getReviews();
        this.description = userDTO.getDescription();
        this.birthdate = userDTO.getBirthdate();
        this.avatarImg = userDTO.getAvatarImg();
        this.backgroundImg = userDTO.getBackgroundImg();
        this.socialLinks = userDTO.getSocialLinks();
    }
}
