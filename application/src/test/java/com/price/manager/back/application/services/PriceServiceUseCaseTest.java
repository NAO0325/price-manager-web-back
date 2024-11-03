package com.price.manager.back.application.services;

import com.price.manager.back.application.ports.driven.PriceRepositoryPort;
import com.price.manager.back.application.utils.PriceDomainMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceUseCaseTest {

    private PriceDomainMocks mocks;

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceServiceUseCase priceServiceUseCase;

    @BeforeEach
    void setUp() {
        mocks = new PriceDomainMocks();
    }

    @Test
    void findByBrandProductBetweenDateTest1() {

        // given
        var dateTest = "2020-06-14 10:00:00";

        // when
        when(priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(anyLong(), anyLong(), any()))
                .thenReturn(mocks.mockListTest1());

        //then
        var result = priceServiceUseCase.findByBrandProductBetweenDate("1", "35455", dateTest);
        var result2 = priceServiceUseCase.findByBrandProductBetweenDate("1", "35455", "Invalid format");
        var result3 = priceServiceUseCase.findByBrandProductBetweenDate("1", "35455", null);
        var result4 = priceServiceUseCase.findByBrandProductBetweenDate("1", "35455", "Monday, January 1, 2023");
        assertEquals(Long.valueOf("1"), result.getBrandId());
        assertEquals(Long.valueOf("35455"), result.getProductId());
        assertEquals(Double.valueOf("35.5"), result.getPrice());
        assertNull(result2);
        assertNull(result3);
        assertNull(result4);
    }

    @Test
    void findByBrandProductBetweenDateTest2() {

        // given
        var dateTest = "2020-06-14 16:00:00";

        // when
        when(priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(anyLong(), anyLong(), any()))
                .thenReturn(mocks.mockListTest2());

        //then
        var result = priceServiceUseCase.findByBrandProductBetweenDate("1", "35455", dateTest);
        assertEquals(Long.valueOf("1"), result.getBrandId());
        assertEquals(Long.valueOf("35455"), result.getProductId());
        assertEquals(Double.valueOf("25.45"), result.getPrice());
    }

    @Test
    void findByBrandProductBetweenDateTest3() {

        // given
        var dateTest = "2020-06-14 21:00:00";

        // when
        when(priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(anyLong(), anyLong(), any()))
                .thenReturn(mocks.mockListTest3());

        //then
        var result = priceServiceUseCase.findByBrandProductBetweenDate("1", "35455", dateTest);
        assertEquals(Long.valueOf("1"), result.getBrandId());
        assertEquals(Long.valueOf("35455"), result.getProductId());
        assertEquals(Double.valueOf("35.5"), result.getPrice());
    }

    @Test
    void findByBrandProductBetweenDateTest4() {

        // given
        var dateTest = "2020-06-15 10:00:00";

        // when
        when(priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(anyLong(), anyLong(), any()))
                .thenReturn(mocks.mockListTest4());

        //then
        var result = priceServiceUseCase.findByBrandProductBetweenDate("1", "3595", dateTest);
        assertEquals(Long.valueOf("1"), result.getBrandId());
        assertEquals(Long.valueOf("35455"), result.getProductId());
        assertEquals(Double.valueOf("30.5"), result.getPrice());
    }

    @Test
    void findByBrandProductBetweenDateTest5() {

        // given
        var dateTest = "2020-06-15 21:00:00";

        // when
        when(priceRepositoryPort.findAllByBrandIdAndProductIdBetweenDates(anyLong(), anyLong(), any()))
                .thenReturn(mocks.mockListTest5());

        //then
        var result = priceServiceUseCase.findByBrandProductBetweenDate("1", "3595", dateTest);
        assertEquals(Long.valueOf("1"), result.getBrandId());
        assertEquals(Long.valueOf("35455"), result.getProductId());
        assertEquals(Double.valueOf("38.95"), result.getPrice());
    }

}
