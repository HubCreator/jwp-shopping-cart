package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.persistence.entity.ProductCategory;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductViewController {
    private final ProductService productService;

    public ProductViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        final List<ProductResponse> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index.html";
    }

    @GetMapping("/admin")
    public String getProducts(final Model model) {
        final List<ProductResponse> products = productService.getProducts();
        model.addAttribute("products", products);
        return "admin.html";
    }

    // TODO: 관리자의 권한으로 접근하면, 소비자가 접근할 때와 다른 화면을 보여줄 필요가 있지 않을까
    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable Long productId, final Model model) {
        final ProductRequest productRequest = productService.getById(productId);
        model.addAttribute("product", productRequest);
        return "product.html";
    }


    @ModelAttribute("categorys")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }

}