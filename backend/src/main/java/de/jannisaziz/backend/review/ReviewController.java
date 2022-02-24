package de.jannisaziz.backend.review;

import de.jannisaziz.backend.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    private boolean isAuthorized(UserRole requiredRole) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> requiredRole.equals(UserRole.valueOf(grantedAuthority.getAuthority())));
    }

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
        if (isAuthorized(UserRole.USER)) try {
            return service.addReview(review);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PatchMapping
    public Review updateReview(@RequestBody Review review) throws ResponseStatusException {
        if (isAuthorized(UserRole.USER)) try {
            return service.updateReview(review);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{reviewId}")
    public Review deleteReviewById(@PathVariable String reviewId) throws ResponseStatusException {
        if (isAuthorized(UserRole.USER)) try {
            return service.deleteReviewById(reviewId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
