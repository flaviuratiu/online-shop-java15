package org.fasttrackit.onlineshop.persistence;

import org.fasttrackit.onlineshop.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // queries by nested properties
    Page<Review> findByProductId(long productId, Pageable pageable);
}
