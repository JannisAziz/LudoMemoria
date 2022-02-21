package de.jannisaziz.backend.review;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository respository;

    public List<Review> findReviewsByUserId(String userId) throws IllegalArgumentException {
        return respository.findReviewsByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public List<Review> findReviewsByGameId(String gameId) throws IllegalArgumentException {
        return respository.findReviewsByGameId(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
    }

    public Review addReview(Review review) throws IllegalArgumentException {
        if (!respository.existsById(review.getId()))
            return respository.save(review);

        throw new IllegalArgumentException("Already exists");
    }

    public Review updateReview(Review review) throws IllegalArgumentException {
        if (respository.existsById(review.getId()))
            return respository.save(review);

        throw new IllegalArgumentException("Not found");
    }

    public Review deleteReviewById(String reviewId) throws IllegalArgumentException {
        Review review = respository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));

        respository.deleteById(review.getId());

        return review;
    }
}
