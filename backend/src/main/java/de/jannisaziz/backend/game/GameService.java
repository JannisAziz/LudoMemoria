package de.jannisaziz.backend.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Game> getGamesByIds(List<String> ids) throws IllegalArgumentException {
        List<Game> games = new ArrayList<>();

        repository.findAllById(ids)
                .forEach(games::add);

        if (games.isEmpty())
            throw NO_GAMES_FOUND_EX(ids.toString());
        else
            return games;
    }

    public Game updateGame(Game game) throws IllegalArgumentException {
        if (repository.existsById(game.getId()))
            return repository.save(game);
        else
            throw NO_GAMES_FOUND_EX(game.getId());
    }
}
