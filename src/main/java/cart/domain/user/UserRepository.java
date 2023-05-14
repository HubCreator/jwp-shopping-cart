package cart.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findUserByEmail(String userEmail);
}
