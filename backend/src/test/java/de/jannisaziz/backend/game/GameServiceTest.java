package de.jannisaziz.backend.game;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private final GameRepository gameRepositoryMock = mock(GameRepository.class);
    private final GameService gameService = new GameService(gameRepositoryMock);

    @Test
    void getGameByIdWhenGameExistsReturnsGame() {

        // Given
        String testId = "TEST_ID";
        Game expected = new Game();
        expected.setId(testId);
        when(gameRepositoryMock.findById(testId)).thenReturn(Optional.of(expected));

        // When
        Game actual = gameService.getGameById(testId);

        // Then
        assertThat(actual.getId(), is(testId));
        assertThat(actual, is(expected));
    }

    @Test
    void getGameByIdWhenGameNotExistsThrowIllArgEx() {

        // Given
        String testId = "TEST_ID";
        when(gameRepositoryMock.findById(testId)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(IllegalArgumentException.class, () -> gameService.getGameById(testId));
    }

    @Test
    void getGamesByIdsWhenGamesExistReturnsGames() {

        // Given
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        List<String> testIds = List.of(id1, id2, id3);

        Game game1 = new Game();
        game1.setId(id1);
        Game game2 = new Game();
        game2.setId(id2);
        Game game3 = new Game();
        game3.setId(id3);

        Iterable<Game> expected = List.of(game1, game2, game3);

        when(gameRepositoryMock.findAllById(testIds)).thenReturn(expected);

        // When
        List<Game> actual = gameService.getGamesByIds(testIds);

        List<String> actualIds = List.of(
                actual.get(0).getId(),
                actual.get(1).getId(),
                actual.get(2).getId()
        );

        // Then
        assertThat(actualIds, is(testIds));
        assertThat(actual, is(expected));
    }

    @Test
    void getGamesByIdsWhenSomeGamesExistReturnsGames() {

        // Given
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        List<String> testIds = List.of(id1, id2, id3);

        Game game1 = new Game();
        game1.setId(id1);
        Game game2 = new Game();
        game2.setId(id2);

        Iterable<Game> expected = List.of(game1, game2);

        when(gameRepositoryMock.findAllById(testIds)).thenReturn(expected);

        // When
        List<Game> actual = gameService.getGamesByIds(testIds);

        List<String> actualIds = List.of(
                actual.get(0).getId(),
                actual.get(1).getId()
        );

        // Then
        assertThat(actualIds, not(testIds));
        assertThat(actual, is(expected));
    }

    @Test
    void getGamesByIdsWhenGamesNotExistThrowsIllArgEx() {

        // Given
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        List<String> testIds = List.of(id1, id2, id3);

        when(gameRepositoryMock.findAllById(testIds)).thenReturn(List.of());

        // When
        // Then
        assertThrows(IllegalArgumentException.class, () -> gameService.getGamesByIds(testIds));
    }

    @Test
    void updateGameWhenGameExistsReturnsGame() {

        // Given
        String testId = "TEST_ID";
        Game expected = new Game();
        expected.setId(testId);

        when(gameRepositoryMock.existsById(expected.getId())).thenReturn(true);
        when(gameRepositoryMock.save(expected)).thenReturn(expected);

        // When
        Game actual = gameService.updateGame(expected);

        // Then
        assertThat(actual.getId(), is(testId));
        assertThat(actual, is(expected));
    }

    @Test
    void updateGameWhenGameNotExistsThrowsIllArgEx() {

        // Given
        String testId = "TEST_ID";
        Game expected = new Game();
        expected.setId(testId);

        when(gameRepositoryMock.existsById(expected.getId())).thenReturn(false);

        // When
        // Then
        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame(expected));
    }
}