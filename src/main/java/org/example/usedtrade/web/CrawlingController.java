package org.example.usedtrade.web;

import lombok.RequiredArgsConstructor;
import org.example.usedtrade.application.CrawlingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/crawl")
public class CrawlingController {

  private final CrawlingService crawlingService;

  @GetMapping("/single")
  public String crawlSingle(Long watchId) {
    crawlingService.crawlOne(watchId);
    return "redirect:/items?watchId=" + watchId;
  }
}
