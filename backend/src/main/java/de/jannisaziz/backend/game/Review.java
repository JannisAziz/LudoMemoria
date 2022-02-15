package de.jannisaziz.backend.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review implements Serializable {

    @Id
    private String id;
    private String userId;
    private String gameId;

    private String description;
    private String dateAdded;
    private int votesUp;
    private int votesDown;
}
