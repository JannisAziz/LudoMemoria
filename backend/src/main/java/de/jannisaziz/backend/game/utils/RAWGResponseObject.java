package de.jannisaziz.backend.game.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.jannisaziz.backend.game.Game;

import java.util.List;

@JsonDeserialize(using = RAWGDeserializer.class)
public record RAWGResponseObject(int count, String next, String previous, List<Game> results) {}
