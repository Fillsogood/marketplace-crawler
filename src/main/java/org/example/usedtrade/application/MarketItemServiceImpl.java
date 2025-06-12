package org.example.usedtrade.application;

import lombok.RequiredArgsConstructor;
import org.example.usedtrade.domain.model.MarketItem;
import org.example.usedtrade.domain.repository.MarketItemRepository;
import org.example.usedtrade.domain.service.MarketItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketItemServiceImpl implements MarketItemService {
  private final MarketItemRepository marketItemRepository;

  @Override
  public void save(MarketItem item) {
    marketItemRepository.save(item);
  }

  @Override
  public List<MarketItem> findByProductWatchId(Long productWatchId) {
    return marketItemRepository.findByProductWatch_Id(productWatchId);
  }

  @Override
  public boolean existsByLink(String link) {
    return marketItemRepository.existsByLink(link);
  }

  @Override
  public List<MarketItem> findByWatchIdSorted(Long watchId, String sort) {
    if ("priceDesc".equals(sort)) {
      return marketItemRepository.findByProductWatchIdOrderByPriceDesc(watchId);
    }
    return marketItemRepository.findByProductWatchIdOrderByPriceAsc(watchId);
  }

  @Override
  public List<MarketItem> findAllSorted(String sort) {
    if ("priceDesc".equals(sort)) {
      return marketItemRepository.findAllByOrderByPriceDesc();
    }
    return marketItemRepository.findAllByOrderByPriceAsc();
  }
}
