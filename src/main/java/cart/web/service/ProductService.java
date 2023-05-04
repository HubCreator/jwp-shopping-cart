package cart.web.service;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.exception.ProductNotFoundException;
import cart.exception.UnexpectedException;
import cart.web.controller.product.dto.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    public final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Long save(final ProductRequest productRequest) {
        final Product product = productRequest.toEntity();
        return productRepository.insert(product);
    }

    public void update(final Long id, final ProductRequest productRequest) {
        final Product product = productRequest.toEntity();
        int updatedCount = productRepository.update(id, product);
        if (updatedCount != 1) {
            throw new ProductNotFoundException(id);
        }
    }

    public void delete(final Long id) {
        int deletedCount = productRepository.deleteById(id);
        if (deletedCount == 0) {
            throw new ProductNotFoundException(id);
        }
        if (deletedCount > 1) {
            log.error("error = {}", "삭제 상황에서 productId가 중복된 상품이 존재합니다. DB를 확인하세요.");
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public Product getById(final Long id) {
        final Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() -> new ProductNotFoundException(id));
    }
}
