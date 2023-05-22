package springbox.jpaflushtest.nooffset;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품의 마지막 상품 번호를 입력받는다.
     */

    @GetMapping("/api/products")
    public void findProducts(@RequestParam(name = "id") Long id) {
        productService.findProducts(id);

    }

}
