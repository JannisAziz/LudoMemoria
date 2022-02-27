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

    public User createUser(User user) throws IllegalStateException {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new IllegalStateException("User already exists.");
        else {
            return userRepository.save(user);
        }
    }

    public UserDTO updateUser(UserDTO userDTO) throws IllegalStateException {
        try {
            User user = userRepository.findByUsernameOrEmail(userDTO.getUsername(), null)
                    .orElseThrow(() -> USR_NF_EX(userDTO.getUsername()));
            user.updateFromDTO(userDTO);
            return userRepository.save(user).asDTO();
        } catch (UsernameNotFoundException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public User confirmUser(String confirmationToken) throws UsernameNotFoundException {
        User user = userRepository.findUserByConfirmationToken(confirmationToken)
                .orElseThrow(() -> USR_NF_EX(confirmationToken));

        user.setEnabled(true);
        return userRepository.save(user);
    }
}
