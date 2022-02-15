package de.jannisaziz.backend.game;

import de.jannisaziz.backend.game.IGDB.IGDBGameRepository;
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
    private final IGDBGameRepository igdbRepository;

    public List<Game> findGamesByName(String name) throws IllegalArgumentException {

        List<Game> searchResults = igdbRepository.searchGamesByName(name);

        if (searchResults.isEmpty())
            throw NO_GAMES_FOUND_EX(name);
        else
            return searchResults;
    }
    public Game findGameById(String id) throws IllegalArgumentException {

        if (!repository.existsById(id)) {
            Game game = igdbRepository.findGameById(id);

            return repository.save(game);
        }
        return repository.findById(id)
                .orElseThrow(() -> NO_GAMES_FOUND_EX(id));
    }
}
