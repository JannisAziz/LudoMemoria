package de.jannisaziz.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedGame {

    @Id
    String id;

    Game game;

    int timePlayed;
    List<String> achievements;
    List<String> notes;
}
