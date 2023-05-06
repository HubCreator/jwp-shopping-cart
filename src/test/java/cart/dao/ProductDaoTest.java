package cart.dao;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ProductDao.class)
class ProductDaoTest {

    private final Product product = new Product(1L, "파스타", "pastaUrl", 20000, ProductCategory.WESTERN);

    @Autowired
    private ProductDao productDao;

    @Test
    void insertAndFind() {
        // given
        final Long id = productDao.insert(product);

        // when
        final Optional<Product> productOptional = productDao.findById(id);
        final Product findProduct = productOptional.get();

        // then
        assertThat(findProduct.getId()).isEqualTo(id);
    }

    @Test
    @Sql("/test.sql")
    void findAll() {
        // given
        final Product product1 = new Product(1L, "파스타", "pastaUrl", 20000, ProductCategory.WESTERN);
        final Product product2 = new Product(2L, "초밥", "chobobUrl", 15000, ProductCategory.JAPANESE);
        final Product product3 = new Product(3L, "치킨", "chickenUrl", 30000, ProductCategory.KOREAN);
        productDao.insert(product1);
        productDao.insert(product2);
        productDao.insert(product3);

        // when
        final List<Product> products = productDao.findAll();

        // then
        assertThat(products).hasSize(3);
    }
}