package de.jannisaziz.backend.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedGame implements Serializable {

    @Id
    private String id;

    private Game game;

    private int timePlayed;
    private List<String> achievements;
    private List<String> notes;
}
