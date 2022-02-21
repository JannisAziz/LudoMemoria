package de.jannisaziz.backend.savedGame;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedGameRepository extends MongoRepository<SavedGame, String> {
    Optional<List<SavedGame>> findByUserId(String userId);
}
