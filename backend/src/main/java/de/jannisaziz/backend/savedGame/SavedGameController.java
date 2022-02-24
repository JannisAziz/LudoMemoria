package de.jannisaziz.backend.savedGame;

import de.jannisaziz.backend.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/savedGames")
public class SavedGameController {

    private final SavedGameService service;

    private boolean isAuthorized(UserRole requiredRole) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> requiredRole.equals(UserRole.valueOf(grantedAuthority.getAuthority())));
    }

    @GetMapping("/userId={userId}")
    public List<SavedGame> findSavedGamesByUserId(@PathVariable String userId) throws ResponseStatusException {
        try {
            return service.findSavedGamesByUserId(userId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "findgamesbyuserid: " + e.getMessage());
        }
    }

    @PutMapping
    public SavedGame addSavedGame(@RequestBody SavedGame savedGame) throws ResponseStatusException {
        if (isAuthorized(UserRole.USER)) try {
            return service.addSavedGame(savedGame);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "addgame:" + e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PatchMapping
    public SavedGame updateSavedGame(@RequestBody SavedGame savedGame) throws ResponseStatusException {
        if (isAuthorized(UserRole.USER)) try {
            return service.updateSavedGame(savedGame);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "updategame:" + e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/id={savedGameId}")
    public void deleteSavedGameById(@PathVariable String savedGameId) throws ResponseStatusException {

        System.out.println("DELETE SAVED GAME: " + savedGameId);

        if (isAuthorized(UserRole.USER)) try {
            service.deleteSavedGame(savedGameId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "delete:" + e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
