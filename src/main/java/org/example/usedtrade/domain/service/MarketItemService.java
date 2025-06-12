package org.example.usedtrade.domain.service;

import org.example.usedtrade.domain.model.MarketItem;

import java.util.List;

public interface MarketItemService {
  void save(MarketItem item);

  List<MarketItem> findByProductWatchId(Long productWatchId);

  boolean existsByLink(String link);

  List<MarketItem> findByWatchIdSorted(Long watchId, String sort);

  List<MarketItem> findAllSorted(String sort);
}
