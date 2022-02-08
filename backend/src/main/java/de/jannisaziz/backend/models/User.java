package de.jannisaziz.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    String id;

    String email;
    String displayName;
    String password;

    List<String> oauthConnections;

    List<SavedGame> savedGames;
    List<Game> wishlistGames;
    List<Review> reviews;

    String description;
    String birthdate;
    String avatarImg;
    String backgroundImg;

    List<String> socialLinks;
}
