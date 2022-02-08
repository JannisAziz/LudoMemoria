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
@Document(collection = "games")
public class Game {

    @Id
    String id;

    String name;
    String logoImg;
    String backgroundImg;

    int votesUp;
    int votesDown;

    List<Review> reviews;
}
