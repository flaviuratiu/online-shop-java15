package org.fasttrackit.onlineshop.persistence;

import org.fasttrackit.onlineshop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
