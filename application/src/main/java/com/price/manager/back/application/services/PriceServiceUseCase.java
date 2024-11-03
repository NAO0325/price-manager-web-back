package com.price.manager.back.application.services;


import com.price.manager.back.application.ports.driven.PriceRepositoryPort;
import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.application.utils.DateUtil;
import com.price.manager.back.domain.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceServiceUseCase implements PriceServicePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price findByBrandProductBetweenDate(String brandId, String productId, String dateBetween) {
        return Optional.of(new SearchCriteria(brandId, productId, dateBetween))
                .map(this::listAllByBrandProductBetweenDate)
                .map(this::filterPrices)
                .flatMap(prices -> prices.stream()
                        .findFirst())
                .orElse(null);
    }

    private List<Price> listAllByBrandProductBetweenDate(SearchCriteria criteria) {
        return Optional.ofNullable(DateUtil.getDateFromString(criteria.dateBetween()))
                .map(date -> priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(
                        Long.parseLong(criteria.brandId()),
                        Long.parseLong(criteria.productId()),
                        date
                ))
                .orElse(List.of());
    }

    private List<Price> filterPrices(List<Price> prices) {
        if (prices.size() <= 1) {
            return prices;
        }
        return Optional.of(prices)
                .map(this::filterSimilarElementsByPriority)
                .map(this::filterSimilarElementsByPriceList)
                .orElse(List.of());
    }

    private List<Price> filterSimilarElementsByPriority(List<Price> prices) {
        var maxPriority = getMaxPriority(prices);
        return prices.stream()
                .filter(price -> price.getPriority() >= maxPriority)
                .toList();
    }

    private List<Price> filterSimilarElementsByPriceList(List<Price> prices) {
        var maxPriceList = getMaxPriceList(prices);
        return prices.stream()
                .filter(price -> price.getPriceList() >= maxPriceList)
                .toList();
    }

    private Long getMaxPriceList(List<Price> prices) {
        return prices.stream()
                .map(Price::getPriceList)
                .max(Comparator.naturalOrder())
                .orElse(0L);
    }

    private Integer getMaxPriority(List<Price> prices) {
        return prices.stream()
                .map(Price::getPriority)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    private record SearchCriteria(String brandId, String productId, String dateBetween) {}

}
