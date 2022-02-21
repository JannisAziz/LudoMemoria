package de.jannisaziz.backend.IGDB;

import de.jannisaziz.backend.game.Game;
import de.jannisaziz.backend.game.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class IGDBSearchService {

    private static IllegalArgumentException NO_GAMES_FOUND_EX(String identifier) {
        return new IllegalArgumentException("No games found: " + identifier);
    }

    private final GameRepository gameRepository;
    private final IGDBGameRepository igdbRepository;

    public List<Game> browseAllByPage(int resultsPerPage, int currentPage) throws IllegalArgumentException {
        List<Game> searchResults = igdbRepository.browseAllByPage(resultsPerPage, currentPage)
                .orElseThrow(() -> NO_GAMES_FOUND_EX(resultsPerPage + "rpp " + currentPage + "pg"));
        return gameRepository.saveAll(searchResults);
    }

    public List<Game> searchByQuery(String query) throws IllegalArgumentException {
        List<Game> searchResults = igdbRepository.searchByQuery(query)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Query!"));

        return gameRepository.saveAll(searchResults);
    }

    public List<Game> searchByName(String name, int resultsPerPage, int currentPage) throws IllegalArgumentException {
        List<Game> searchResults = igdbRepository.searchGamesByName(name, resultsPerPage, currentPage)
                .orElseThrow(() -> NO_GAMES_FOUND_EX(name));

        return gameRepository.saveAll(searchResults);
    }

    public List<String> getGenres() throws IllegalArgumentException {
        return igdbRepository.getGenres()
                .orElseThrow(() -> new IllegalArgumentException("No genres found"));
    }

    public List<Game> searchByGenre(String genre, int resultsPerPage, int currentPage) throws IllegalArgumentException {
        List<Game> searchResults = igdbRepository.searchGamesByGenre(genre, resultsPerPage, currentPage)
                .orElseThrow(() -> NO_GAMES_FOUND_EX(genre));

        return gameRepository.saveAll(searchResults);
    }

    public List<Game> searchByRelease(int releaseTime, int resultsPerPage, int currentPage) throws IllegalArgumentException {
        List<Game> searchResults = igdbRepository.searchGamesByRelease(releaseTime, resultsPerPage, currentPage)
                .orElseThrow(() -> NO_GAMES_FOUND_EX(String.valueOf(releaseTime)));

        return gameRepository.saveAll(searchResults);
    }
}
