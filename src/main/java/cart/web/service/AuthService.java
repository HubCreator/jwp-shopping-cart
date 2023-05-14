package cart.web.service;

import cart.domain.user.User;
import cart.domain.user.UserEmail;
import cart.domain.user.UserRepository;
import cart.exception.UserNotFoundException;
import cart.web.controller.cart.dto.AuthCredentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidUser(final AuthCredentials authCredentials) {
        final UserEmail userEmail = new UserEmail(authCredentials.getEmail());

        final Optional<User> userOptional1 = userRepository.findUserByEmail(authCredentials.getEmail());
        final User user = userOptional1.orElseThrow(() -> new UserNotFoundException(userEmail));

        return Objects.equals(authCredentials.getPassword(), user.getUserPasswordValue());
    }
}
