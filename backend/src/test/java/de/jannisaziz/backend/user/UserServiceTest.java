package de.jannisaziz.backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepositoryMock = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepositoryMock);


    @Test
    void getUserByIdWhenUserExistsReturnsUser() {

        // Given
        String testId = "TEST_ID";

        User expected = new User();
        expected.setId(testId);

        when(userRepositoryMock.findById(testId)).thenReturn(Optional.of(expected));

        // When
        User actual = userService.getUserById(testId);

        // Then
        assertThat(actual, is(expected));
        assertThat(actual.getId(), is(expected.getId()));
    }

    @Test
    void getUserByIdWhenUserNotExistsThrowsNotFoundEx() {

        // Given
        String testId = "TEST_ID";

        when(userRepositoryMock.findById(testId)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(testId));
    }


    @Test
    void findUserWithUsernameEmailOrConfirmationTokenWhenUserExistsReturnsUser() {

        // Given
        String testUsername = "testUsername";
        String testEmail = "test@email.com";
        String testToken = "aqRGSAHETHwj2§$WEnstnfHWS%$§%ZQWSTHG";

        User expected = new User();
        expected.setUsername(testUsername);
        expected.setEmail(testEmail);
        expected.setConfirmationToken(testToken);

        when(userRepositoryMock.findByUsernameOrEmail(null, testEmail)).thenReturn(Optional.of(expected));
        when(userRepositoryMock.findByUsernameOrEmail(testUsername, null)).thenReturn(Optional.of(expected));
        when(userRepositoryMock.findUserByConfirmationToken(testToken)).thenReturn(Optional.of(expected));

        // When
        User actualWithUsername = userService.findUser(null, testEmail);
        User actualWithEmail = userService.findUser(testUsername, null);
        User actualWithToken = userService.findUserByConfirmationToken(testToken);

        // Then
        assertThat(actualWithUsername, is(expected));
        assertThat(actualWithEmail, is(expected));
        assertThat(actualWithToken, is(expected));
        assertThat(actualWithUsername.getUsername(), is(expected.getUsername()));
        assertThat(actualWithEmail.getEmail(), is(expected.getEmail()));
        assertThat(actualWithToken.getConfirmationToken(), is(expected.getConfirmationToken()));
    }

    @Test
    void findUserWithUsernameEmailOrConfirmationTokenWhenUserNotExistsThrowsNotFoundEx() {

        // Given
        String testUsername = "testUsername";
        String testEmail = "test@email.com";
        String testToken = "aqRGSAHETHwj2§$WEnstnfHWS%$§%ZQWSTHG";

        when(userRepositoryMock.findByUsernameOrEmail(testUsername, null)).thenReturn(Optional.empty());
        when(userRepositoryMock.findByUsernameOrEmail(null, testEmail)).thenReturn(Optional.empty());
        when(userRepositoryMock.findUserByConfirmationToken(testToken)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(UsernameNotFoundException.class, () -> userService.findUser(testUsername, null));
        assertThrows(UsernameNotFoundException.class, () -> userService.findUser(null, testEmail));
        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByConfirmationToken(testToken));
    }


    @Test
    void createUserWhenUserNotExistsReturnsUser() {

        // Given
        String testUsername = "testUsername";
        String testEmail = "test@email.com";

        User expected = new User();
        expected.setUsername(testUsername);
        expected.setEmail(testEmail);

        when(userRepositoryMock.existsByUsername(testUsername)).thenReturn(false);
        when(userRepositoryMock.save(expected)).thenReturn(expected);

        // When
        User actual = userService.createUser(expected);

        // Then
        assertThat(actual, is(expected));
        assertThat(actual.getUsername(), is(testUsername));
        assertThat(actual.getEmail(), is(testEmail));
    }

    @Test
    void createUserWhenUserExistsThrowsIllStateEx() {

        // Given
        String testUsername = "testUsername";

        User expected = new User();
        expected.setUsername(testUsername);

        when(userRepositoryMock.existsByUsername(testUsername)).thenReturn(true);

        // When
        // Then
        assertThrows(IllegalStateException.class, () -> userService.createUser(expected));
    }


    @Test
    void updateUserWhenUserExistsReturnsUser() {

        // Given
        String testUsername = "testUsername";
        String testEmail = "test@email.com";

        User expected = new User();
        expected.setUsername(testUsername);
        expected.setEmail(testEmail);

        when(userRepositoryMock.findByUsernameOrEmail(testUsername, null)).thenReturn(Optional.of(expected));
        when(userRepositoryMock.save(expected)).thenReturn(expected);

        // When
        UserDTO actual = userService.updateUser(expected.asDTO());

        // Then
        assertThat(actual, is(expected.asDTO()));
        assertThat(actual.getUsername(), is(testUsername));
        assertThat(actual.getEmail(), is(testEmail));
    }

    @Test
    void updateUserWhenUserNotExistsThrowsNotFoundEx() {

        // Given
        String testUsername = "testUsername";
        String testEmail = "test@email.com";

        User expected = new User();
        expected.setUsername(testUsername);
        expected.setEmail(testEmail);

        when(userRepositoryMock.findByUsernameOrEmail(testUsername, null)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(IllegalStateException.class, ()-> userService.updateUser(expected.asDTO()));
    }


    @Test
    void confirmUserWhenUserExistsReturnsUser() {

        // Given
        String testToken = "asifn345Z$§%HEwtbw456h§%&§h5r6j3h";

        User expected = new User();
        expected.setConfirmationToken(testToken);

        when(userRepositoryMock.findUserByConfirmationToken(testToken)).thenReturn(Optional.of(expected));
        when(userRepositoryMock.save(expected)).thenReturn(expected);

        // When
        User actual = userService.confirmUser(testToken);

        // Then
        assertThat(actual, is(expected));
        assertThat(actual.getConfirmationToken(), is(testToken));
    }

    @Test
    void confirmUserWhenUserNotExitsThrowsNotFoundEx() {

        // Given
        String testToken = "asifn345Z$§%HEwtbw456h§%&§h5r6j3h";

        User expected = new User();
        expected.setConfirmationToken(testToken);

        when(userRepositoryMock.findUserByConfirmationToken(testToken)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(UsernameNotFoundException.class, () -> userService.confirmUser(testToken));
    }
}
