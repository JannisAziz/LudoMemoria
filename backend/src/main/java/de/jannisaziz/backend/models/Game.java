package de.jannisaziz.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "games")
public class Game {

    @Id
    String id;

    String name;

    public Game(String name) {
        this.name = name;
    }
}
