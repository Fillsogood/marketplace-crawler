package org.example.usedtrade.domain.service;

import org.example.usedtrade.domain.model.ProductWatch;

import java.util.List;

public interface ProductWatchService {
  void save(ProductWatch productWatch);
  List<ProductWatch> getAll();
}
