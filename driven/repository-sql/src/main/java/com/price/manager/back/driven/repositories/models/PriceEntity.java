package com.price.manager.back.driven.repositories.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
@Getter
@Setter
@NoArgsConstructor
public class PriceEntity implements Serializable {

  @Id
  @Column(name = "PRICE_LIST", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long priceList;

  @Column(name = "START_DATE", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "END_DATE", nullable = false)
  private LocalDateTime endDate;

  @Column(name = "BRAND_ID", nullable = false)
  private Long brandId;

  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @Column(name = "PRIORITY", nullable = false)
  private Integer priority;

  @Column(name = "PRICE", nullable = false)
  private Double price;

  @Column(name = "CURR", nullable = false)
  private String curr;

}
