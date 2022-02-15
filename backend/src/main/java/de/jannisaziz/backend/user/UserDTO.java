package de.jannisaziz.backend.user;

import de.jannisaziz.backend.game.Game;
import de.jannisaziz.backend.game.Review;
import de.jannisaziz.backend.game.SavedGame;
import lombok.*;
import org.springframework.data.annotation.Id;
import java.util.List;

@Builder
@Getter
@Setter
public class UserDTO {

    @Id
    private String id;

    private String username;
    private String email;

    private List<String> oauthConnections;

    private List<SavedGame> savedGames;
    private List<Game> wishlistGames;
    private List<Review> reviews;

    private String description;
    private String birthdate;
    private String avatarImg;
    private String backgroundImg;

    private List<String> socialLinks;
}
