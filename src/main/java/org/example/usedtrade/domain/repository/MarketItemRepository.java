package org.example.usedtrade.domain.repository;

import org.example.usedtrade.domain.model.MarketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketItemRepository extends JpaRepository<MarketItem, Long> {
  List<MarketItem> findByProductWatch_Id(Long productWatchId);
  boolean existsByLink(String link);

  // 감시 상품별 가격 오름차순 정렬
  List<MarketItem> findByProductWatchIdOrderByPriceAsc(Long productWatchId);

  // 감시 상품별 가격 내림차순 정렬
  List<MarketItem> findByProductWatchIdOrderByPriceDesc(Long productWatchId);

  // 전체 항목 가격 오름차순 정렬
  List<MarketItem> findAllByOrderByPriceAsc();

  // 전체 항목 가격 내림차순 정렬
  List<MarketItem> findAllByOrderByPriceDesc();

  List<MarketItem> findByProductWatchId(Long watchId);
}
