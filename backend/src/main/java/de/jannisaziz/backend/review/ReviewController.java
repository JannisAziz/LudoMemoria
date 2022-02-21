package de.jannisaziz.backend.review;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    @GetMapping("/userId={userId}")
    public List<Review> findReviewsByUserId(@PathVariable String userId) throws ResponseStatusException {
        try {
            return service.findReviewsByUserId(userId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/gameId={gameId}")
    public List<Review> findReviewsByGameId(@PathVariable String gameId) throws ResponseStatusException {
        try {
            return service.findReviewsByGameId(gameId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public Review addReview(@RequestBody Review review) throws ResponseStatusException {
        try {
            return service.addReview(review);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping
    public Review updateReview(@RequestBody Review review) throws ResponseStatusException {
        try {
            return service.updateReview(review);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{reviewId}")
    public Review deleteReviewById(@PathVariable String reviewId) throws ResponseStatusException {
        try {
            return service.deleteReviewById(reviewId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
