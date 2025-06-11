package org.example.usedtrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UsedTradeApplication {

  public static void main(String[] args) {
    SpringApplication.run(UsedTradeApplication.class, args);
  }

}
