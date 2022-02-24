package de.jannisaziz.backend.IGDB;

import de.jannisaziz.backend.game.Game;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/search")
public class IGDBSearchController {

    private final IGDBSearchService service;

    @GetMapping("/browse&rpp={resultsPerPage}&pg={currentPage}")
    public List<Game> browseAllByPage(@PathVariable int resultsPerPage, @PathVariable int currentPage) throws ResponseStatusException {
        try {
            return service.browseAllByPage(resultsPerPage, currentPage);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/query={query}")
    public List<Game> searchByQuery(@PathVariable String query) throws ResponseStatusException {
        try {
            return service.searchByQuery(query);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/name={name}&rpp={resultsPerPage}&pg={currentPage}")
    public List<Game> searchByName(@PathVariable String name, @PathVariable int resultsPerPage, @PathVariable int currentPage) throws ResponseStatusException {
        try {
            return service.searchByName(name, resultsPerPage, currentPage);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/genres")
    public List<String> getGenres() throws ResponseStatusException {
        try {
            return service.getGenres();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/genre={genre}&rpp={resultsPerPage}&pg={currentPage}")
    public List<Game> searchByGenre(@PathVariable String genre, @PathVariable int resultsPerPage, @PathVariable int currentPage) throws ResponseStatusException {
        try {
            return service.searchByGenre(genre, resultsPerPage, currentPage);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/release={releaseTime}&rpp={resultsPerPage}&pg={currentPage}")
    public List<Game> searchByRelease(@PathVariable int releaseTime, @PathVariable int resultsPerPage, @PathVariable int currentPage) throws ResponseStatusException {
        try {
            return service.searchByRelease(releaseTime, resultsPerPage, currentPage);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
