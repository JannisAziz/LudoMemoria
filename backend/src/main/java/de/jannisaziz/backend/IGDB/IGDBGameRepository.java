package de.jannisaziz.backend.IGDB;

import de.jannisaziz.backend.game.Game;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Repository
public class IGDBGameRepository {

    private static final WebClient webClient = WebClient.create();
    private static final String IGDB_API_URI = "https://api.igdb.com/v4/";
    private static final String GAME_FIELDS =
            "fields id, game.name, game.summary, game.cover.image_id, game.rating, game.screenshots.image_id, game.first_release_date;";

    private static final List<IGDBDeserializer.IGDBGenreResult> GENRES = new ArrayList<>();

    private Optional<List<Game>> searchIGDB(String searchQuery) {
        return Optional.ofNullable(webClient
                .post()
                .uri(IGDB_API_URI + "search/")
                .header("Client-ID", IGDBAuth.getClientId())
                .header("Authorization", "Bearer " + IGDBAuth.getAccessToken())
                .header("Content-Type", "application/apicalypse")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(searchQuery)
                .retrieve()
                .bodyToFlux(IGDBDeserializer.IGDBGameResult.class)
                .map(IGDBDeserializer.IGDBGameResult::game)
                .collectList()
                .block());
    }

    private Optional<List<IGDBDeserializer.IGDBGenreResult>> getGenresIGDB(String query) {
        return Optional.ofNullable(webClient
                .post()
                .uri(IGDB_API_URI + "genres/")
                .header("Client-ID", IGDBAuth.getClientId())
                .header("Authorization", "Bearer " + IGDBAuth.getAccessToken())
                .header("Content-Type", "application/apicalypse")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(query)
                .retrieve()
                .bodyToFlux(IGDBDeserializer.IGDBGenreResult.class)
                .collectList()
                .block());
    }

    public Optional<List<Game>> searchByQuery(String query) throws NoSuchElementException {
        return searchIGDB(query);
    }

    public Optional<List<Game>> browseAllByPage(int resultsPerPage, int currentPage) throws NoSuchElementException {
        String where = "where %s ;".formatted("game != n & game.name != n");
        String limit = "limit %s ;".formatted(resultsPerPage);
        String offset = "offset %s ;".formatted(resultsPerPage + currentPage);

        String query = GAME_FIELDS + where + limit + offset;

        return searchIGDB(query);
    }

    public Optional<List<Game>> searchGamesByName(String name, int resultsPerPage, int currentPage) {
        String where = "where %s ;".formatted("game != n & game.name != n");
        String limit = "limit %s ;".formatted(resultsPerPage);
        String offset = "offset %s ;".formatted(resultsPerPage + currentPage);

        String query = "search \"" + name + "\"; " +
                GAME_FIELDS + where + limit + offset;

        return searchIGDB(query);
    }

    public Optional<List<String>> getGenres() throws IllegalArgumentException {
        String query = "fields id, slug; limit 50; sort slug;";
        List<IGDBDeserializer.IGDBGenreResult> results = getGenresIGDB(query)
                .orElseThrow(() -> new IllegalArgumentException("No genres found"));

        GENRES.clear();
        GENRES.addAll(results);

        return Optional.of(results.stream().map(IGDBDeserializer.IGDBGenreResult::name).toList());
    }

    public Optional<List<Game>> searchGamesByGenre(String genre, int resultsPerPage, int currentPage) {
        if (GENRES.stream().anyMatch(r -> r.name().equals(genre))) try {
                List<Integer> genreIds = GENRES.stream().filter(r -> r.name().equals(genre)).map(IGDBDeserializer.IGDBGenreResult::id).toList();

                String where = "where %s ;".formatted("where game != n & game.name != n & game.genres = " + genreIds);
                String sort = "sort %s ;".formatted("game.rating desc");
                String limit = "limit %s ;".formatted(resultsPerPage);
                String offset = "offset %s ;".formatted(resultsPerPage + currentPage);

                String query = GAME_FIELDS + sort + where + limit + offset;

                return searchIGDB(query);
        } catch (Exception e) {
            throw new IllegalArgumentException("No genres found: " + genre);
        } else
            throw new IllegalArgumentException("No genres found: " + genre);
    }

    public Optional<List<Game>> searchGamesByRelease(int releaseTime, int resultsPerPage, int currentPage) {
        String where = "where %s ;".formatted("game != n & game.name != n & game.first_release_date >= " + releaseTime);
        String sort = "sort %s ;".formatted("game.first_release_date desc");
        String limit = "limit %s ;".formatted(resultsPerPage);
        String offset = "offset %s ;".formatted(resultsPerPage + currentPage);

        String query = GAME_FIELDS + sort + where + limit + offset;

        return searchIGDB(query);
    }
}
