package de.jannisaziz.backend.savedGame;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SavedGameService {

    private static IllegalArgumentException NO_GAMES_FOUND_EX(String identifier) {
        return new IllegalArgumentException("No saved games found: " + identifier);
    }

    private final SavedGameRepository repository;

    public List<SavedGame> findSavedGamesByUserId(String userId) throws IllegalArgumentException {
        return repository.findByUserId(userId)
                .orElseThrow(() -> NO_GAMES_FOUND_EX(userId));
    }

    public SavedGame addSavedGame(SavedGame savedGame) throws IllegalArgumentException {
        if (!repository.existsById(savedGame.getId()))
            return repository.save(savedGame);
        else throw new IllegalArgumentException("Saved game id already exists: " + savedGame.getId());

    }

    public SavedGame updateSavedGame(SavedGame savedGame) throws IllegalArgumentException {
        if (repository.existsById(savedGame.getId()))
            return repository.save(savedGame);
        else throw NO_GAMES_FOUND_EX(savedGame.getId());
    }

    public void deleteSavedGame(String savedGameId) throws IllegalArgumentException {
        if (repository.existsById(savedGameId)){
            repository.deleteById(savedGameId);
        }
        else throw NO_GAMES_FOUND_EX(savedGameId);
    }
}
