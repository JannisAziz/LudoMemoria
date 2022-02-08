package de.jannisaziz.backend.controllers;

import de.jannisaziz.backend.models.User;
import de.jannisaziz.backend.repositories.UserRepository;
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
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;
    private final WebClient webTestClient = WebClient.create();

    @MockBean
    private UserRepository userRepositoryMock;

    // Request functions

    private ResponseEntity<User> requestGetUserById(String userId) {
        return webTestClient
                .get()
                .uri("http://localhost:" + port + "/api/users/" + userId)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .toEntity(User.class)
                .block();
    }

    @Test
    void getUserByIdWithExistingUserReturnsUserAndOk() {

        // GIVEN
        String testId = "TEST_ID";
        User expected = new User();
        expected.setId(testId);
        when(userRepositoryMock.findById(testId)).thenReturn(Optional.of(expected));

        // WHEN
        ResponseEntity<User> getResponse = requestGetUserById(testId);

        // THEN
        assertThat(getResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getResponse.getBody(), is(expected));
    }

    @Test
    void getUserByIdWithNonExistingUserReturnsNotFound() {

        // GIVEN
        String testId = "TEST_ID";
        when(userRepositoryMock.findById(testId)).thenReturn(Optional.empty());

        // WHEN
        ResponseEntity<User> getResponse = requestGetUserById(testId);

        // THEN
        assertThat(getResponse.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

}
