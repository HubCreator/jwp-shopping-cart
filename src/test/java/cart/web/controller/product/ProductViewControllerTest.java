package cart.web.controller.product;

import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.web.controller.auth.LoginCheckInterceptor;
import cart.web.controller.auth.LoginUserArgumentResolver;
import cart.web.controller.config.WebConfig;
import cart.web.controller.product.dto.ProductResponse;
import cart.web.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.ModelMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@WebMvcTest(value = ProductViewController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        WebConfig.class, LoginCheckInterceptor.class, LoginUserArgumentResolver.class
                }

        ))
class ProductViewControllerTest {

    /**
     * MockMvc는 웹 애플리케이션의 컨트롤러를 테스트하기 위한 라이브러리로,
     * HTTP 요청 및 응답을 가상으로 처리하여 컨트롤러의 동작을 테스트할 수 있게 해줍니다.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * @MockBean 애너테이션은 Spring Boot의 테스트 기능 중 하나로,
     * 테스트에서 사용할 가짜(Mock) 빈을 생성하고 등록하는 역할을 합니다.
     */
    @MockBean
    private ProductService productService;

    private final Product product = new Product(1L, "chicken", "chickenUrl", 30000, ProductCategory.KOREAN);

    @Test
    void renderProduct() throws Exception {
        // given, when
        Mockito.when(productService.getById(ArgumentMatchers.any())).thenReturn(product);

        // then
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final ModelMap modelMap = mvcResult.getModelAndView().getModelMap();
        final ProductResponse productResponse = (ProductResponse) modelMap.get("product");
        assertAll(
                () -> assertThat(productResponse.getId()).isEqualTo(1),
                () -> assertThat(productResponse.getName()).isEqualTo("chicken"),
                () -> assertThat(productResponse.getImageUrl()).isEqualTo("chickenUrl"),
                () -> assertThat(productResponse.getPrice()).isEqualTo(30000),
                () -> assertThat(productResponse.getCategory()).isEqualTo(ProductCategory.KOREAN)
        );
    }
}