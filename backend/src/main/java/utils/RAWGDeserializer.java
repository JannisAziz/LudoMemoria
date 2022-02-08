package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.jannisaziz.backend.models.Game;

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

        List<Game> results = new ArrayList<>();
        rootNode.get("results")
                .elements().forEachRemaining(
                        gameNode -> results.add(
                                new Game(
                                        gameNode.get("id").asText(),
                                        gameNode.get("name").asText()
                                )
                        )
                );

        return new RAWGResponseObject(
                count,
                next,
                previous,
                results
        );
    }
}
