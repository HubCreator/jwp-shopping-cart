package cart.web.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.exception.GlobalException;
import cart.web.controller.product.dto.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private ProductRequest productRequest;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest("스테이크", "steakUrl", "40000", ProductCategory.WESTERN);
    }

    @DisplayName("상품을 저장한다")
    @Test
    void save() {
        // given
        final List<Product> productEntities = List.of(new Product("스테이크", "steakUrl", "40000", ProductCategory.WESTERN));
        when(productDao.insert(any())).thenReturn(1L);
        when(productDao.findAll()).thenReturn(productEntities);

        // when
        productService.save(productRequest);

        // then
        final List<Product> products = productService.getProducts();
        assertAll(
                () -> assertThat(products).hasSize(1),
                () -> assertThat(products.get(0).getProductNameValue()).isEqualTo("스테이크")
        );
    }

    @DisplayName("존재하는 상품을 조회한다")
    @Test
    void findProduct_success() {
        // given
        final Product productEntity = new Product("스테이크", "steakUrl", "40000", ProductCategory.WESTERN);
        when(productDao.findById(any())).thenReturn(Optional.of(productEntity));

        // when
        final Product product = productService.getById(1L);

        // then
        assertAll(
                () -> assertThat(product.getProductNameValue()).isEqualTo("스테이크"),
                () -> assertThat(product.getImageUrlValue()).isEqualTo("steakUrl"),
                () -> assertThat(product.getPriceValue()).isEqualTo(40000),
                () -> assertThat(product.getCategory()).isEqualTo(ProductCategory.WESTERN)
        );
    }

    @DisplayName("존재하지 않는 상품을 조회하면 예외가 발생한다")
    @Test
    void findProduct_fail() {
        // given
        when(productDao.findById(any())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productService.getById(1L))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("전체 상품을 조회한다")
    @Test
    void getProducts() {
        // given
        final List<Product> productEntities = List.of(
                new Product("치킨", "chickenUrl", "20000", ProductCategory.KOREAN),
                new Product("초밥", "chobobUrl", "30000", ProductCategory.JAPANESE),
                new Product("스테이크", "steakUrl", "40000", ProductCategory.WESTERN)
        );
        when(productDao.findAll()).thenReturn(productEntities);

        // when
        final List<Product> products = productService.getProducts();

        // then
        assertAll(
                () -> assertThat(products).hasSize(3),
                () -> assertThat(products.get(0).getProductNameValue()).isEqualTo("치킨"),
                () -> assertThat(products.get(1).getProductNameValue()).isEqualTo("초밥"),
                () -> assertThat(products.get(2).getProductNameValue()).isEqualTo("스테이크")
        );
    }

    @DisplayName("상품 수정을 정상적으로 진행한다")
    @Test
    void update_success() {
        // given
        when(productDao.update(any(), any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> productService.update(1L, productRequest));
    }

    @DisplayName("상품 수정이 실패하면 예외가 발생한다")
    @Test
    void update_fail() {
        // given
        when(productDao.update(any(), any())).thenReturn(0);

        // when, then
        assertThatThrownBy(() -> productService.update(1L, productRequest))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("상품 삭제를 정상적으로 진행한다")
    @Test
    void delete_success() {
        // given
        when(productDao.deleteById(any())).thenReturn(1);

        // when, then
        assertDoesNotThrow(() -> productService.delete(1L));
    }

    @DisplayName("상품 삭제가 실패하면 예외가 발생한다")
    @Test
    void delete_fail() {
        // given
        when(productDao.deleteById(any())).thenReturn(0);

        // when, then
        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("상품이 2개 이상 삭제되면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void delete_fail(int count) {
        // given
        when(productDao.deleteById(any())).thenReturn(count);

        // when, then
        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(GlobalException.class);
    }
}
