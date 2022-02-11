package de.jannisaziz.backend.game.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.jannisaziz.backend.game.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RAWGDeserializer extends JsonDeserializer<RAWGResponseObject> {

    @Override
    public RAWGResponseObject deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode rootNode = oc.readTree(jsonParser);

        int count = rootNode.get("count").asInt();

        String next = rootNode.get("next").asText();
        String previous = rootNode.get("previous").asText();

        List<Game> games = new ArrayList<>();
        rootNode.get("results")
                .elements().forEachRemaining(
                        gameNode -> games.add(
                                new Game(
                                        gameNode.get("id").asText(),
                                        gameNode.get("name").asText(),
                                        "",
                                        gameNode.get("background_image").asText(),
                                        0,
                                        0,
                                        List.of()
                                )
                        )
                );

        return new RAWGResponseObject(
                count,
                next,
                previous,
                games
        );
    }
}
