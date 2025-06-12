package org.example.usedtrade.web;

import lombok.RequiredArgsConstructor;
import org.example.usedtrade.domain.model.MarketItem;
import org.example.usedtrade.domain.model.ProductWatch;
import org.example.usedtrade.domain.repository.ProductWatchRepository;
import org.example.usedtrade.domain.service.MarketItemService;
import org.example.usedtrade.domain.service.ProductWatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class MarketItemController {

  private final MarketItemService marketItemService;
  private final ProductWatchService productWatchService;

  @GetMapping
  public String listItems(@RequestParam(required = false) Long watchId,
                          @RequestParam(required = false, defaultValue = "priceAsc") String sort,
                          Model model) {

    List<MarketItem> items;

    if (watchId != null) {
      items = marketItemService.findByWatchIdSorted(watchId, sort);
    } else {
      items = marketItemService.findAllSorted(sort);
    }

    List<ProductWatch> watches = productWatchService.getAll();

    model.addAttribute("items", items);
    model.addAttribute("watches", watches);
    model.addAttribute("selectedWatchId", watchId);
    model.addAttribute("sort", sort);

    return "marketItems"; // templates/marketItems.html
  }
}
