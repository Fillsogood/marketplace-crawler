package org.example.usedtrade.application;

import lombok.RequiredArgsConstructor;
import org.example.usedtrade.domain.model.ProductWatch;
import org.example.usedtrade.domain.repository.ProductWatchRepository;
import org.example.usedtrade.domain.service.ProductWatchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductWatchServiceImpl implements ProductWatchService {
  private final ProductWatchRepository productWatchRepository;

  @Override
  public void save(ProductWatch productWatch) {
    productWatchRepository.save(productWatch);
  }

  @Override
  public List<ProductWatch> getAll() {
    return productWatchRepository.findAll();
  }
}
