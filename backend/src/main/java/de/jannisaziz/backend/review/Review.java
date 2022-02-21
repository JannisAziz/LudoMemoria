package de.jannisaziz.backend.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reviews")
public class Review implements Serializable {

    @Id
    private String id;
    private String userId;
    private String username;
    private String gameId;
    private String gameName;

    private String description;
    private String dateAdded;
    private int votesUp;
    private int votesDown;
}
