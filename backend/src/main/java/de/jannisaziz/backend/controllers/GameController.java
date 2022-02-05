package de.jannisaziz.backend.controllers;

import de.jannisaziz.backend.models.Game;
import de.jannisaziz.backend.models.Review;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("games")
public class GameController {

    // Game mappings

    // Only get games stored in local DB... external DB doesn't allow 'data dump'
    @GetMapping
    public List<Game> getGames() {
        return null;
    }

    @GetMapping("{gameId}")
    public Game getGame(@PathVariable String gameId) {
        return null;
    }

    // Admin access only
    @PostMapping
    public String addGame(@RequestBody Game game) {
        return null;
    }

    @DeleteMapping("{gameId}")
    public String deleteGame(@PathVariable String gameId) {
        return null;
    }

    @PatchMapping("{gameId}")
    public String updateGame(@PathVariable String gameId, @RequestBody Game game) {
        return null;
    }

    // Review mappings

    @GetMapping("{gameId}/reviews")
    public List<Review> getReviews(@PathVariable String gameId) {
        return null;
    }

    @PutMapping("{gameId}/reviews")
    public String addReview(@PathVariable String gameId, @RequestBody Review review) {
        return null;
    }

    @DeleteMapping("{gameId}/reviews")
    public String deleteReview(@PathVariable String gameId, @RequestBody Review review) {
        return null;
    }

    @PatchMapping("{gameId}/reviews")
    public String updateReview(@PathVariable String gameId, @RequestBody Review review) {
        return null;
    }

    // Library & Wishlist mappings

    @PostMapping("{userId}/library/{gameId}")
    public String addGameToLibrary(@PathVariable String userId, @PathVariable String gameId) {
        return null;
    }

    @DeleteMapping("{userId}/library/{gameId}")
    public String deleteGameFromLibrary(@PathVariable String userId, @PathVariable String gameId) {
        return null;
    }

    @PostMapping("{userId}/wishlist/{gameId}")
    public String addGameToWishlist(@PathVariable String userId, @PathVariable String gameId) {
        return null;
    }

    @DeleteMapping("{userId}/wishlist/{gameId}")
    public String deleteGameFromWishlist(@PathVariable String userId, @PathVariable String gameId) {
        return null;
    }
}
