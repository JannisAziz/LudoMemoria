package de.jannisaziz.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedGame {

    @Id
    String id;

    String name;

    public SavedGame(String name) {
        this.name = name;
    }
}
