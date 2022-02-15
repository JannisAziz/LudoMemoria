package de.jannisaziz.backend.game.IGDB;

import de.jannisaziz.backend.game.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Repository
public class IGDBGameRepository {

    private static final WebClient webClient = WebClient.create();
    private static final String IGDB_API_URI = "https://api.igdb.com/v4/";

    public List<Game> searchGamesByName(String name) {
        String query = "search \"" + name + "\"; " +
                "fields game.id, game.name, game.summary, game.cover.url; " +
                "where game != n & game.name != n; " +
                "limit 50;";

        ResponseEntity<IGDBDeserializer.IGDBSearchResult> searchResults = webClient
                .post()
                .uri(IGDB_API_URI + "search/")
                .header("Client-ID", IGDBAuth.getClientId())
                .header("Authorization", "Bearer " + IGDBAuth.getAccessToken())
                .header("Content-Type", "application/apicalypse")
                .header("Accept", "application/json")
                .bodyValue(query)
                .retrieve()
                .toEntity(IGDBDeserializer.IGDBSearchResult.class)
                .block();
        if (searchResults != null && searchResults.hasBody()) {
            return searchResults.getBody().games();
        } else return List.of();
    }

    public Game findGameById(String id) {
        String query = "fields id, name, summary, cover.url; " +
                "where id = " + id + ";";

        ResponseEntity<IGDBDeserializer.IGDBGameResult> result = webClient
                .post()
                .uri(IGDB_API_URI + "games/")
                .header("Client-ID", IGDBAuth.getClientId())
                .header("Authorization", "Bearer " + IGDBAuth.getAccessToken())
                .header("Content-Type", "application/apicalypse")
                .header("Accept", "application/json")
                .bodyValue(query)
                .retrieve()
                .toEntity(IGDBDeserializer.IGDBGameResult.class)
                .block();

        if (result != null && result.hasBody())
            return result.getBody().game();
        else return null;
    }
}
