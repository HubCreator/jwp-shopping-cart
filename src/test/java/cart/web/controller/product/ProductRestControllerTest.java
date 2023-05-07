package cart.web.controller.product;

import cart.domain.product.ProductCategory;
import cart.web.controller.product.dto.ProductRequest;
import cart.web.service.ProductService;
import cart.web.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;
    @MockBean
    private UserService userService;

    private final ProductRequest productRequest = new ProductRequest("chicken", "chickenUrl", 30000, ProductCategory.KOREAN);

    @Test
    void add() throws Exception {
        // given
        Mockito.when(productService.save(ArgumentMatchers.any())).thenReturn(1L);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/products/1"));
    }

    @Test
    void update() throws Exception {
        // given
        final ProductRequest updateRequest = new ProductRequest("!chicken", "!chickenUrl", 20000, ProductCategory.WESTERN);

        // when
        Mockito.doNothing().when(productService).update(ArgumentMatchers.any(), ArgumentMatchers.any());

        // then
        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void delete() throws Exception {
        // given, when
        Mockito.doNothing().when(productService).delete(ArgumentMatchers.any());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}