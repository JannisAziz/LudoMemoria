package de.jannisaziz.backend.user;

import de.jannisaziz.backend.savedGame.SavedGame;
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

    private List<SavedGame> savedGames;
}
