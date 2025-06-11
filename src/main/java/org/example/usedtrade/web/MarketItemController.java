package org.example.usedtrade.web;

import lombok.RequiredArgsConstructor;
import org.example.usedtrade.domain.model.MarketItem;
import org.example.usedtrade.domain.model.ProductWatch;
import org.example.usedtrade.domain.repository.MarketItemRepository;
import org.example.usedtrade.domain.repository.ProductWatchRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class MarketItemController {

  private final MarketItemRepository marketItemRepository;
  private final ProductWatchRepository productWatchRepository;

  @GetMapping
  public String listItems(@RequestParam(required = false) Long watchId,
                          @RequestParam(required = false, defaultValue = "priceAsc") String sort,
                          Model model) {

    List<MarketItem> items;

    if (watchId != null) {
      items = marketItemRepository.findByProductWatchId(watchId);
    } else {
      items = marketItemRepository.findAll();
    }

    // 정렬 처리
    if (sort.equals("priceAsc")) {
      items.sort(Comparator.comparingInt(MarketItem::getPrice));
    } else if (sort.equals("priceDesc")) {
      items.sort(Comparator.comparingInt(MarketItem::getPrice).reversed());
    }

    List<ProductWatch> watches = productWatchRepository.findAll();

    model.addAttribute("items", items);
    model.addAttribute("watches", watches);
    model.addAttribute("selectedWatchId", watchId);
    model.addAttribute("sort", sort);

    return "marketItems"; // templates/marketItems.html
  }
}
