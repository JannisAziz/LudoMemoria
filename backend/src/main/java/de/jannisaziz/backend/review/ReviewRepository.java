package de.jannisaziz.backend.review;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    Optional<List<Review>> findReviewsByUserId(String userId);

    Optional<List<Review>> findReviewsByGameId(String gameId);
}
