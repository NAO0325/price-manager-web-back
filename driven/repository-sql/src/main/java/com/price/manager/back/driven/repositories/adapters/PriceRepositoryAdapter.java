package com.price.manager.back.driven.repositories.adapters;


import com.price.manager.back.application.ports.driven.PriceRepositoryPort;
import com.price.manager.back.domain.Price;
import com.price.manager.back.driven.repositories.PriceJpaRepository;
import com.price.manager.back.driven.repositories.mappers.PriceEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository repository;

    private final PriceEntityMapper mapper;

    @Override
    public List<Price> findAllByBrandIdAndProductIdBetweenDates(Long brandId, Long productId, LocalDateTime dateBetween) {
        return repository.findAllByBrandIdAndProductIdBetweenDates(brandId, productId, dateBetween)
                .orElseGet(ArrayList::new)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
