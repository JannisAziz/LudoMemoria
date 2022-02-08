package de.jannisaziz.backend.services;

import de.jannisaziz.backend.models.Game;
import de.jannisaziz.backend.repositories.GameRepository;
import de.jannisaziz.backend.repositories.RAWGGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;
    private final RAWGGameRepository rawgRepository;

    public GameService(GameRepository repository, RAWGGameRepository rawgRepository) {
        this.repository = repository;
        this.rawgRepository = rawgRepository;
    }

    public List<Game> findGamesByName(String name) throws IllegalArgumentException {
        List<Game> searchResults = rawgRepository.findGamesByName(name);
        //List<Game> searchResults = repository.findGamesByNameRegex(name.toLowerCase());

        if (searchResults.isEmpty())
            throw new IllegalArgumentException("No games with name '" + name + "' found!");
        else
            return searchResults;
    }
}
