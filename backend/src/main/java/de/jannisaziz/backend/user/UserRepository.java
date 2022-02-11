package de.jannisaziz.backend.user;

import com.mongodb.lang.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(@Nullable String username, @Nullable String email);

    Optional<User> findUserByConfirmationToken(String confirmationToken);
}
