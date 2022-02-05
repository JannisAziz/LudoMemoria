package de.jannisaziz.backend.models;

import java.util.List;

public class Game {

    String gameID;
    GameData gameData;

    List<Review> reviews;
    int votesUp;
    int votesDown;

}
