package de.jannisaziz.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    String id;
    String userId;
    String gameId;

    String description;
    String dateAdded;
    int votesUp;
    int votesDown;
}
