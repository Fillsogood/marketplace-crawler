package org.example.usedtrade.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.usedtrade.domain.model.MarketItem;
import org.example.usedtrade.domain.model.ProductWatch;
import org.example.usedtrade.domain.model.ProductWatchStatus;
import org.example.usedtrade.domain.repository.MarketItemRepository;
import org.example.usedtrade.domain.repository.ProductWatchRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingService {

  private final ProductWatchRepository productWatchRepository;
  private final MarketItemRepository marketItemRepository;

  public void crawlOne(Long watchId) {
    ProductWatch watch = productWatchRepository.findById(watchId).orElseThrow();
    try {
      watch.setStatus(ProductWatchStatus.CRAWLING);
      productWatchRepository.save(watch);

      String keyword = watch.getProductName();

      // 사이트별 크롤링 결과 합치기
      List<MarketItem> allItems = new ArrayList<>();
      allItems.addAll(crawlBunjang(keyword, watch));
      allItems.addAll(crawlJoonggonara(keyword, watch));

      for (MarketItem item : allItems) {
        if (!marketItemRepository.existsByLink(item.getLink())) {
          marketItemRepository.save(item);
        }
      }

      watch.setStatus(ProductWatchStatus.DONE);
      productWatchRepository.save(watch);
      log.info("✅ {}: 총 {}건 수집 완료", keyword, allItems.size());

    } catch (Exception e) {
      log.error("❌ {} 크롤링 실패", watch.getProductName(), e);
      watch.setStatus(ProductWatchStatus.IDLE);
      productWatchRepository.save(watch);
    }
  }


  private List<MarketItem> crawlBunjang(String keyword, ProductWatch watch) {
    List<MarketItem> items = new ArrayList<>();

    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    WebDriver driver = new ChromeDriver();

    try {
      String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
      String url = "https://m.bunjang.co.kr/search/products?q=" + encodedKeyword;
      driver.get(url);

      Thread.sleep(3000 + ThreadLocalRandom.current().nextInt(1000));

      List<WebElement> cards = driver.findElements(By.cssSelector("a[href^='/products/']"));
      log.info("전체 제품 링크 수: {}", cards.size());

      for (WebElement card : cards) {
        try {
          String href = card.getAttribute("href");
          if (href == null) continue;
          if (!href.startsWith("http")) {
            href = "https://m.bunjang.co.kr" + href;
          }

          // 제목
          WebElement titleElement = card.findElement(By.cssSelector(".sc-RcBXQ"));
          String title = titleElement.getText().trim();
          log.info("📌 제목: {}", title);

          // 가격
          WebElement priceElement = card.findElement(By.cssSelector(".cPlkrx"));
          String priceText = priceElement.getText().replaceAll("[^0-9]", "");
          int price = priceText.isEmpty() ? 0 : Integer.parseInt(priceText);
          log.info("💰 가격: {}", price);

          WebElement imgElement = card.findElement(By.tagName("img"));
          String imageUrl = imgElement.getAttribute("src");


          MarketItem item = new MarketItem(title, price, href, "번개장터", watch, imageUrl);
          items.add(item);
          log.info("✔ 수집 완료: {}", item);

        } catch (Exception e) {
          log.warn("❌ 항목 파싱 실패", e);
        }
      }

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      driver.quit();
    }

    return items;
  }

  private List<MarketItem> crawlJoonggonara(String keyword, ProductWatch watch) {
    List<MarketItem> items = new ArrayList<>();
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    WebDriver driver = new ChromeDriver();

    try {
      String encoded = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
      String url = "https://web.joongna.com/search?keyword=" + encoded;
      driver.get(url);
      Thread.sleep(4000);

      List<WebElement> cards = driver.findElements(By.cssSelector("a[href^='/product/']"));
      for (WebElement card : cards) {
        try {
          // 링크
          String link = card.getAttribute("href");
          if (!link.startsWith("http")) {
            link = "https://web.joongna.com" + link;
          }

          // 이미지
          WebElement imgTag = card.findElement(By.cssSelector("img"));
          String imageUrl = imgTag.getAttribute("src");

          // 제목
          WebElement titleTag = card.findElement(By.cssSelector("h2"));
          String title = titleTag.getText().trim();

          // 가격
          WebElement priceTag = card.findElement(By.cssSelector("div.font-semibold"));
          String priceText = priceTag.getText().replaceAll("[^0-9]", "");
          int price = priceText.isEmpty() ? 0 : Integer.parseInt(priceText);

          MarketItem item = new MarketItem(title, price, link, "중고나라", watch, imageUrl);
          items.add(item);
          log.info("✔ 중고나라 수집 완료: {}", item);
        } catch (Exception e) {
          log.warn("❌ 중고나라 항목 파싱 실패", e);
        }
      }

    } catch (Exception e) {
      log.error("중고나라 크롤링 실패", e);
    } finally {
      driver.quit();
    }

    return items;
  }
}

