package com.price.manager.back.application.ports.driven;

import com.price.manager.back.domain.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
   
   List<Price> findAllByBrandIdAndProductIdBetweenDates(Long brandId, Long productId, LocalDateTime dateBetween);

}
