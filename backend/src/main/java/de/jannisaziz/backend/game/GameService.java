package de.jannisaziz.backend.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GameService {

    private static IllegalArgumentException NO_GAMES_FOUND_EX(String identifier) {
        return new IllegalArgumentException("No games found: " + identifier);
    }

    private final GameRepository repository;
    private final RAWGGameRepository rawgRepository;

    private boolean isOnline = true;

    public List<Game> findGamesByName(String name) throws IllegalArgumentException {

        List<Game> searchResults;

        if (isOnline)
            searchResults = rawgRepository.findGamesByName(name);
        else
            searchResults = repository.findGamesByNameRegex(name.toLowerCase());

        if (searchResults.isEmpty())
            throw NO_GAMES_FOUND_EX(name);
        else
            return searchResults;
    }
}
