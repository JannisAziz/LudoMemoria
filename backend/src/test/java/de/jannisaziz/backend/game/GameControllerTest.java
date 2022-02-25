package de.jannisaziz.backend.game;

import de.jannisaziz.backend.security.AuthHelpers;
import de.jannisaziz.backend.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest {

    @LocalServerPort
    private int port;
    private final WebClient webTestClient = WebClient.create();

    @MockBean
    private GameRepository gameRepositoryMock;
    @MockBean
    private AuthHelpers authHelpers;

    // Request Functions

    private ResponseEntity<Game> requestFindGameById(String gameId) {
        return webTestClient
                .get()
                .uri("http://localhost:" + port + "/api/games/" + gameId)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .toEntity(Game.class)
                .block();
    }

    private ResponseEntity<List<Game>> requestFindGamesByIds(List<String> gameIds) {
        return webTestClient
                .post()
                .uri("http://localhost:" + port + "/api/games/ids")
                .bodyValue(gameIds)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .toEntityList(Game.class)
                .block();
    }

    private ResponseEntity<Game> requestUpdateGame(Game game) {
        return webTestClient
                .patch()
                .uri("http://localhost:" + port + "/api/games/update")
                .bodyValue(game)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .toEntity(Game.class)
                .block();
    }

    @Test
    void findGameByIdWhenGameExistsReturnsGameAndHTTPOk() {

        // Given
        String testId = "TEST_ID";
        Game expected = new Game();
        expected.setId(testId);

        when(gameRepositoryMock.findById(testId)).thenReturn(Optional.of(expected));

        // When
        ResponseEntity<Game> response = requestFindGameById(testId);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
    }

    @Test
    void findGameByIdWhenGameNotExistsThrowHTTPBadRequest() {

        // Given
        String testId = "TEST_ID";

        when(gameRepositoryMock.findById(testId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Game> response = requestFindGameById(testId);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void findGamesByIdsWhenGamesExistReturnsGamesAndHTTPOk() {

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

        List<Game> expected = List.of(game1, game2, game3);

        when(gameRepositoryMock.findAllById(testIds)).thenReturn(expected);

        // When
        ResponseEntity<List<Game>> response = requestFindGamesByIds(testIds);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        List<Game> actualGames = response.getBody();
        if (actualGames == null)
            fail("Games is null");

        assertThat(actualGames, is(expected));

        List<String> actualIds = List.of(
                actualGames.get(0).getId(),
                actualGames.get(1).getId(),
                actualGames.get(2).getId()
        );
        assertThat(testIds, is(actualIds));
    }

    @Test
    void findGamesByIdsWhenSomeGamesExistReturnsGamesAndHTTPOk() {

        // Given
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        List<String> testIds = List.of(id1, id2, id3);

        Game game1 = new Game();
        game1.setId(id1);
        Game game2 = new Game();
        game2.setId(id2);

        List<Game> expected = List.of(game1, game2);

        when(gameRepositoryMock.findAllById(testIds)).thenReturn(expected);

        // When
        ResponseEntity<List<Game>> response = requestFindGamesByIds(testIds);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        List<Game> actualGames = response.getBody();
        if (actualGames == null)
            fail("Games[] is null");

        assertThat(actualGames, is(expected));

        List<String> actualIds = List.of(
                actualGames.get(0).getId(),
                actualGames.get(1).getId()
        );

        assertThat(testIds, not(actualIds));
    }

    @Test
    void findGamesByIdsWhenGamesNotExistReturnHTTPBadRequest() {

        // Given
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        List<String> testIds = List.of(id1, id2, id3);

        when(gameRepositoryMock.findAllById(testIds)).thenReturn(List.of());

        // When
        ResponseEntity<List<Game>> response = requestFindGamesByIds(testIds);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void updateGameWhenGameExistsReturnsGameAndHTTPOk() {

        // Given
        String testId = "TEST_ID";
        Game expected = new Game();
        expected.setId(testId);

        doReturn(true).when(authHelpers).isAuthorized(UserRole.USER);
        when(gameRepositoryMock.existsById(expected.getId())).thenReturn(true);
        when(gameRepositoryMock.save(expected)).thenReturn(expected);

        // When
        ResponseEntity<Game> response = requestUpdateGame(expected);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expected));
    }

    @Test
    void updateGameWhenGameNotExistsReturnsHTTPBadRequest() {

        // Given
        String testId = "TEST_ID";
        Game expected = new Game();
        expected.setId(testId);

        doReturn(true).when(authHelpers).isAuthorized(UserRole.USER);
        when(gameRepositoryMock.existsById(expected.getId())).thenReturn(false);

        // When
        ResponseEntity<Game> response = requestUpdateGame(expected);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
