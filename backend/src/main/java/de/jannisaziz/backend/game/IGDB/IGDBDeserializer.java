package de.jannisaziz.backend.game.IGDB;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.jannisaziz.backend.game.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IGDBDeserializer {

    @JsonDeserialize(using = IGDBDeserializer.SearchResult.class)
    static record IGDBSearchResult(List<Game> games) { }

    @JsonDeserialize(using = IGDBDeserializer.GameResult.class)
    static record IGDBGameResult(Game game) { }

    static class SearchResult extends JsonDeserializer<IGDBSearchResult> {

        @Override
        public IGDBSearchResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            ObjectCodec oc = jsonParser.getCodec();
            JsonNode rootNode = oc.readTree(jsonParser);

            List<Game> games = new ArrayList<>();
            rootNode.elements().forEachRemaining(
                    n -> {
                        String id = n.findPath("game").findPath("id").asText("NO-ID");
                        String name = n.findPath("name").asText("NO-NAME");
                        String summary = n.findPath("summary").asText("NO-DESC");
                        String imageUrl = n.findPath("url").asText("NO-IMG");

                        games.add(new Game(id, name, summary, imageUrl, 0, 0, List.of()));
                    }
            );

            return new IGDBSearchResult(games);
        }
    }

    static class GameResult extends JsonDeserializer<IGDBGameResult> {

        @Override
        public IGDBGameResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            ObjectCodec oc = jsonParser.getCodec();
            JsonNode rootNode = oc.readTree(jsonParser);

            JsonNode gameNode = rootNode.elements().next();

            String id = gameNode.findPath("id").asText("NO-ID");
            String name = gameNode.findPath("name").asText("NO-NAME");
            String summary = gameNode.findPath("summary").asText("NO-DESC");
            String imageUrl = gameNode.findPath("url").asText("NO-IMG");

            Game game = new Game(id, name, summary, imageUrl, 0, 0, List.of());

            return new IGDBGameResult(game);
        }
    }

}


