package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {

    Long insert(User user, Long productId);

    List<Product> findAllByUser(User user);
}
