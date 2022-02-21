package de.jannisaziz.backend.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GameService {

    private static IllegalArgumentException NO_GAMES_FOUND_EX(String identifier) {
        return new IllegalArgumentException("No games found: " + identifier);
    }

    private final GameRepository repository;

    public Game getGameById(String id) throws IllegalArgumentException {
        return repository.findById(id)
            .orElseThrow(() -> NO_GAMES_FOUND_EX(id));
    }

    public Game updateGame(Game game) throws IllegalArgumentException {
        if (repository.existsById(game.getId()))
            return repository.save(game);
        else
            throw NO_GAMES_FOUND_EX(game.getId());
    }
}
