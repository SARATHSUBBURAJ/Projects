package com.example.directed_payments.service;

import com.example.directed_payments.model.Product;
import com.example.directed_payments.model.SelectedProduct;
import com.example.directed_payments.repository.ProductRepository;
import com.example.directed_payments.repository.SelectedProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedProductService {

    private final ProductRepository productRepository;
    private final SelectedProductRepository selectedProductRepository;

    public SelectedProductService(ProductRepository productRepository, SelectedProductRepository selectedProductRepository) {
        this.productRepository = productRepository;
        this.selectedProductRepository = selectedProductRepository;
    }

    public List<Product> saveSelectedProducts(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        List<SelectedProduct> savedSelections = products.stream()
                .map(product -> {
                    SelectedProduct sp = new SelectedProduct();
                    sp.setProduct(product);
                    return selectedProductRepository.save(sp);
                })
                .toList();

        return savedSelections.stream()
                .map(SelectedProduct::getProduct)
                .toList();
    }

    public List<Product> getSelectedProducts() {
        return selectedProductRepository.findAll().stream()
                .map(SelectedProduct::getProduct)
                .toList();
    }
}
