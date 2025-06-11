package org.example.usedtrade.domain.repository;

import org.example.usedtrade.domain.model.ProductWatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductWatchRepository extends JpaRepository<ProductWatch, Long> {
}
