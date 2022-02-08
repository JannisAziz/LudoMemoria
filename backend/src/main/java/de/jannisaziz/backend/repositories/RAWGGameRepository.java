package de.jannisaziz.backend.repositories;

import de.jannisaziz.backend.models.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import utils.RAWGResponseObject;

import java.util.List;

@Repository
public class RAWGGameRepository {

    private static final WebClient webClient = WebClient.create();

    private static final String RAWG_APU_URL = "https://api.rawg.io/api/";
    private static final String RAWG_API_KEY = "a333a242a03d409f90562cc6b6dc4803";

    public List<Game> findGamesByName(String name) {

        String searchUri = RAWG_APU_URL + "games?key=" + RAWG_API_KEY + "&search=" + name;

        ResponseEntity<RAWGResponseObject> responseEntity = search(searchUri);

        if (responseEntity != null) {
            RAWGResponseObject responseObject = responseEntity.getBody();
            if (responseObject != null) {
                return responseObject.results();
            }
        }

        return List.of();

    }

    private ResponseEntity<RAWGResponseObject> search(String searchUri) {
        return webClient
                .get()
                .uri(searchUri)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> Mono.empty())
                .toEntity(RAWGResponseObject.class)
                .block();
    }
}
