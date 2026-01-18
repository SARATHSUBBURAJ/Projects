package com.example.directed_payments.controller;

import com.example.directed_payments.model.Product;
import com.example.directed_payments.model.SelectedProduct;
import com.example.directed_payments.repository.ProductRepository;
import com.example.directed_payments.repository.SelectedProductRepository;
import com.example.directed_payments.service.ProductService;
import com.example.directed_payments.service.SelectedProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;
    private final SelectedProductService selectedProductService;
    private final SelectedProductRepository selectedProductRepository;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService,
                             SelectedProductService selectedProductService,
                             ProductRepository productRepository,
                             SelectedProductRepository selectedProductRepository) {
        this.productService = productService;
        this.selectedProductService = selectedProductService;
        this.selectedProductRepository = selectedProductRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/add")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    @PostMapping("/chosen-products")
    public List<Product> selectProducts(@RequestBody List<Product> products) {
        List<Product> existingProducts = products.stream()
                .map(p -> productRepository.findByName(p.getName())
                        .orElseThrow(() -> new RuntimeException("Product not found: " + p.getName())))
                .toList();

        List<SelectedProduct> savedSelections = existingProducts.stream()
                .map(p -> {
                    SelectedProduct sp = new SelectedProduct();
                    sp.setProduct(p);
                    return selectedProductRepository.save(sp);
                })
                .toList();

        return savedSelections.stream()
                .map(SelectedProduct::getProduct)
                .toList();
    }

    @GetMapping("/selected-products")
    public List<Product> getSelectedProducts() {
        return selectedProductService.getSelectedProducts();
    }

}

