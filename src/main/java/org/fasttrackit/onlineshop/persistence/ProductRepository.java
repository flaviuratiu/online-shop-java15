package org.fasttrackit.onlineshop.persistence;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
