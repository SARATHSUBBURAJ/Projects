package com.example.directed_payments.repository;

import com.example.directed_payments.model.SelectedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedProductRepository extends JpaRepository<SelectedProduct, Long> {
}
