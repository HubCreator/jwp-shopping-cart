package cart.web.service;

import cart.domain.cart.CartProduct;
import cart.domain.cart.CartRepository;
import cart.domain.user.User;
import cart.domain.user.UserEmail;
import cart.domain.user.UserRepository;
import cart.exception.UserNotFoundException;
import cart.web.controller.cart.dto.AuthCredentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartService(final UserRepository userRepository, final CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public List<CartProduct> getCartProducts1(final User userRequest) {
        final Optional<User> userOptional = userRepository.findUserByEmail(userRequest.getUserEmailValue());
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(userRequest.getUserEmail()));

        return cartRepository.findAllByUser1(user);
    }

    public List<CartProduct> getCartProducts2(final AuthCredentials authCredentials) {
        final String userEmail = authCredentials.getEmail();
        final Optional<User> userOptional = userRepository.findUserByEmail(userEmail);
        final User user = userOptional.orElseThrow(() -> new UserNotFoundException(new UserEmail(userEmail)));

        return cartRepository.findAllByUser2(user);
    }

    @Transactional
    public Long add(final AuthCredentials authCredentials, final Long productId) {
        final String userEmail = authCredentials.getEmail();
        final Optional<User> userOptional = userRepository.findUserByEmail(userEmail);
        userOptional.orElseThrow(() -> new UserNotFoundException(new UserEmail(userEmail)));

        return cartRepository.insert(userOptional.get().getId(), productId);
    }

    @Transactional
    public void delete(final AuthCredentials authCredentials, final Long cartProductId) {
        final String userEmail = authCredentials.getEmail();
        final Optional<User> userOptional = userRepository.findUserByEmail(userEmail);
        userOptional.orElseThrow(() -> new UserNotFoundException(new UserEmail(userEmail)));

        cartRepository.delete(cartProductId);
    }
}
