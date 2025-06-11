package org.example.usedtrade.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class ProductWatch {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String productName;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  private ProductWatchStatus status = ProductWatchStatus.IDLE;
}
