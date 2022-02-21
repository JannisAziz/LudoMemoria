package de.jannisaziz.backend.game;

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
@Document(collection = "games")
public class Game implements Serializable {

    @Id
    private String id;

    private String name;
    private String description;

    private String coverId;
    private List<String> screenshotIds;
}
