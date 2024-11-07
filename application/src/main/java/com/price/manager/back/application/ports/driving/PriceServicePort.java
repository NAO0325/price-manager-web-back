package com.price.manager.back.application.ports.driving;


import com.price.manager.back.domain.Price;

import java.time.LocalDateTime;

public interface PriceServicePort {

   Price findByBrandProductBetweenDate(Long brandId, Long productId, LocalDateTime dateBetween);

}
