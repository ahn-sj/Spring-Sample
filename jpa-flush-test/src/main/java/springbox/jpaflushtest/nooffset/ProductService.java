package springbox.jpaflushtest.nooffset;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void findProducts(Long id) {

        productRepository.findProductsById(id);

    }
}
