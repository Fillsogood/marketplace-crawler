package org.example.usedtrade.web;

import lombok.RequiredArgsConstructor;
import org.example.usedtrade.domain.model.ProductWatch;
import org.example.usedtrade.domain.service.ProductWatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/watches")
public class ProductWatchController {
  private final ProductWatchService productWatchService;

  @GetMapping
  public String list(Model model) {
    model.addAttribute("watches", productWatchService.getAll());
    return "productWatchList";
  }

  @PostMapping
  public String add(@ModelAttribute ProductWatch productWatch) {
    System.out.println("등록 요청: " + productWatch.getCreatedAt()); // 로그 확인
    productWatchService.save(productWatch);
    return "redirect:/watches";
  }
}
