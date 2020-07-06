package org.fasttrackit.onlineshop.persistence;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContaining(String partialName, Pageable pageable);

    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(String partialName, int minimumQuantity, Pageable pageable);

    // JPQL syntax
    @Query(value = "SELECT product FROM Product product WHERE " +
            "(:partialName IS null OR product.name LIKE %:partialName%) AND " +
            "(:minimumQuantity IS null OR product.quantity >= :minimumQuantity)")
    Page<Product> findByOptionalCriteria(String partialName, Integer minimumQuantity, Pageable pageable);
}
