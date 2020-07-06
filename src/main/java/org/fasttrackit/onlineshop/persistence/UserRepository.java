package org.fasttrackit.onlineshop.persistence;

import org.fasttrackit.onlineshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
