package de.jannisaziz.backend.user;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private static UsernameNotFoundException USR_NF_EX(String userIdentifier) {
            return new UsernameNotFoundException("User not found:" + userIdentifier);
    }

    public User getUserById(String userId) throws UsernameNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> USR_NF_EX(userId));
    }

    /**
     * @deprecated (Necessary due to UserDetailsService implementation)
     */
    @Deprecated(since = "implementation")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(username, null)
                .orElseThrow(() -> new UsernameNotFoundException("User not found (" + username + ")"));
    }

    public User findUser(@Nullable String username, @Nullable String email) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(username, email)
                .orElseThrow(() -> USR_NF_EX(username != null ? username : email));
    }

    public User findUserByConfirmationToken(String token) throws UsernameNotFoundException {
        return userRepository.findUserByConfirmationToken(token)
                .orElseThrow(() -> USR_NF_EX(token));
    }

    public String createUser(User user) throws IllegalStateException {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new IllegalStateException("User with email already exists.");
        else {
            userRepository.save(user);
            return "User created";
        }
    }

    public String updateUser(UserDTO userDTO) throws IllegalStateException {
        try {
            User user = userRepository.findByUsernameOrEmail(userDTO.getUsername(), null)
                    .orElseThrow(() -> USR_NF_EX(userDTO.getUsername()));
            user.updateFromDTO(userDTO);
            userRepository.save(user);
            return "User updated";
        } catch (UsernameNotFoundException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public String confirmUser(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(null, email)
                .orElseThrow(() -> USR_NF_EX(email));

        user.setEnabled(true);
        userRepository.save(user);

        return "User confirmed";
    }
}
