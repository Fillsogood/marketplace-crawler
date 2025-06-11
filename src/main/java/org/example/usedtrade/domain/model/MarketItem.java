package org.example.usedtrade.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private int price;
  private String link;
  private String site; // 예: "BUNGE", "JOONGGO"
  private String imageUrl;
  private LocalDateTime createAt;

  @ManyToOne(fetch = FetchType.LAZY)
  private ProductWatch productWatch;

  public MarketItem(String title, int price, String link, String site, ProductWatch watch, String imageUrl) {
    this.title = title;
    this.price = price;
    this.link = link;
    this.site = site;
    this.imageUrl = imageUrl;
    this.createAt = LocalDateTime.now();
    this.productWatch = watch;
  }
}
