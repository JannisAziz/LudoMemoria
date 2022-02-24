package de.jannisaziz.backend.IGDB;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.jannisaziz.backend.game.Game;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IGDBDeserializer {

    @JsonDeserialize(using = IGDBDeserializer.GameResult.class)
    static record IGDBGameResult(Game game) { }

    @JsonDeserialize(using = IGDBDeserializer.GenreResult.class)
    static record IGDBGenreResult(Integer id, String name) { }

    private static class GameResult extends JsonDeserializer<IGDBGameResult> {

        @Override
        public IGDBGameResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            ObjectCodec oc = jsonParser.getCodec();
            JsonNode gameNode = oc.readTree(jsonParser);

            String id = gameNode.findPath("checksum").asText("NO-ID");
            String name = gameNode.findPath("name").asText("NO-NAME");
            String summary = gameNode.findPath("summary").asText("N/A");

            String coverId = gameNode.findPath("cover").findPath("image_id").asText("nocover");

            List<String> screenshotIds = new ArrayList<>();
            gameNode.findPath("screenshots").elements().forEachRemaining(
                    screenshot -> screenshotIds.add(screenshot.findPath("image_id").asText())
            );

            Game game = new Game(id, name, summary, coverId, screenshotIds);

            return new IGDBGameResult(game);
        }
    }

    private static class GenreResult extends JsonDeserializer<IGDBGenreResult> {

        @Override
        public IGDBGenreResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            ObjectCodec oc = jsonParser.getCodec();
            JsonNode genreNode = oc.readTree(jsonParser);

            Integer id = genreNode.findPath("id").asInt();
            String name = genreNode.findPath("slug").asText("NO-GENRE");

            return new IGDBGenreResult(id, name);
        }
    }
}
