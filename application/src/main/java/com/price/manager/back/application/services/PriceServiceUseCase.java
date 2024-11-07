package com.price.manager.back.application.services;


import com.price.manager.back.application.exceptions.PriceNotFoundException;
import com.price.manager.back.application.ports.driven.PriceRepositoryPort;
import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.domain.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class PriceServiceUseCase implements PriceServicePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price findByBrandProductBetweenDate(Long brandId, Long productId, LocalDateTime dateBetween) {

        return priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(brandId, productId, dateBetween)
                .stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(brandId, productId, dateBetween));
    }
}
