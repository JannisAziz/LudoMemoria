package de.jannisaziz.backend.game;

import de.jannisaziz.backend.security.AuthHelpers;
import de.jannisaziz.backend.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService service;
    private final AuthHelpers authHelpers;


    @GetMapping("/{id}")
    public Game findGameById(@PathVariable String id) throws ResponseStatusException {
        try {
            return service.getGameById(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/ids")
    public List<Game> findGamesByIds(@RequestBody List<String> ids) throws ResponseStatusException {
        try {
            return service.getGamesByIds(ids);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/update")
    public Game updateGame(@RequestBody Game game) throws ResponseStatusException {
        if (authHelpers.isAuthorized(UserRole.USER)) try {
            return service.updateGame(game);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
