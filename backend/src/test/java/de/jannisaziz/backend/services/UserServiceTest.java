package de.jannisaziz.backend.services;

import de.jannisaziz.backend.models.User;
import de.jannisaziz.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserRepository userRepositoryMock = mock(UserRepository.class);

    private UserService userService = new UserService(userRepositoryMock);

    @Test
    void getUserByIdWithExistingUserReturnsUser() {

        // GIVEN
        String testId = "TEST_ID";
        User expected = new User(testId, List.of());
        when(userRepositoryMock.findById(testId)).thenReturn(Optional.of(expected));

        // WHEN
        User actual = userService.getUserById(testId);

        // THEN
        assertThat(actual, is(expected));

    }

    @Test
    void getUserByIdWithNonExistingUserThrowsIllegalArgument() {

        // GIVEN
        String testId = "TEST_ID";
        when(userRepositoryMock.findById(testId)).thenThrow(new IllegalArgumentException());

        // THEN
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(testId));
    }
}