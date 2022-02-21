package de.jannisaziz.backend.savedGame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "savedGames")
public class SavedGame implements Serializable {

    @Id
    private String id;
    private String userId;
    private String gameId;

    private List<String> notes;
}
