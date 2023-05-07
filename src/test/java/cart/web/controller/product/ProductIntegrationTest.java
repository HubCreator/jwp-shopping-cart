package cart.web.controller.product;

import cart.domain.product.ProductCategory;
import cart.web.controller.product.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test.sql")
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    final ProductRequest productRequest = new ProductRequest("치킨", "chickenUrl", 20000, ProductCategory.KOREAN);

    private void addProduct(final ProductRequest productRequest) {
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().body(productRequest).post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/products/1");
    }

    @Test
    void 상품을_추가하고_세부_정보를_확인한다() {
        // given, when
        addProduct(productRequest);

        // then
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/products/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_HTML_VALUE);
    }

    @Test
    void 올바르지_않은_상품을_추가하면_예외가_발생한다() {
        // given
        final ProductRequest badProductRequest = new ProductRequest("", "", 99999999, ProductCategory.KOREAN);

        // when, then
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().body(badProductRequest).post("/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("상품 이름은 비어있을 수 없습니다."),
                        "message", containsString("올바르지 않은 입력입니다. 입력 가능한 범위 : 0 ~ 999999")
                );
    }

    @Test
    void 상품을_업데이트한다() {
        // given, when
        final ProductRequest updateProduct = new ProductRequest("초밥", "초밥URl", 20000, ProductCategory.JAPANESE);
        addProduct(productRequest);

        // then
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().body(updateProduct).put("/products/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @Test
    void 상품을_삭제한다() {
        // given
        addProduct(productRequest);

        // when, then
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/1")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
