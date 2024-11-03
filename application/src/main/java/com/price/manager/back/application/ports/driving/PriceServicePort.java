package com.price.manager.back.application.ports.driving;


import com.price.manager.back.domain.Price;

public interface PriceServicePort {

   Price findByBrandProductBetweenDate(String brandId, String productId, String dateBetween);

}
