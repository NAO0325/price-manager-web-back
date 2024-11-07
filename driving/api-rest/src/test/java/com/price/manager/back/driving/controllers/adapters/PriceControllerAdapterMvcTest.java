package com.price.manager.back.driving.controllers.adapters;

import com.price.manager.back.application.exceptions.PriceNotFoundException;
import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.error.CustomExceptionHandler;
import com.price.manager.back.driving.controllers.mappers.PriceMapper;
import com.price.manager.back.driving.controllers.utils.PriceMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceControllerAdapter.class)
@ContextConfiguration(classes = {PriceControllerAdapter.class, CustomExceptionHandler.class})
public class PriceControllerAdapterMvcTest {

    private PriceMocks mocks;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceMapper priceMapper;

    @MockBean
    private PriceServicePort priceServicePort;

    @BeforeEach
    public void setup() {
        mocks = new PriceMocks();
    }

    @Test
    void whenValidInputTest1_thenReturns200() throws Exception {
        // Given
        when(priceServicePort.findByBrandProductBetweenDate(anyLong(), anyLong(), any()))
                .thenReturn(Price.builder().build());
        when(priceMapper.toResponseDto(any()))
                .thenReturn(mocks.createPriceResponse1());

        // When & Then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.startDate").value("2020-06-14 00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31 23:59:59"));
    }

    @Test
    void whenValidInputTest2_thenReturns200() throws Exception {
        // Given
        when(priceServicePort.findByBrandProductBetweenDate(anyLong(), anyLong(), any()))
                .thenReturn(Price.builder().build());
        when(priceMapper.toResponseDto(any()))
                .thenReturn(mocks.createPriceResponse2());

        // When & Then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .param("dateQuery", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.startDate").value("2020-06-14 15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14 18:30:00"));
    }

    @Test
    void whenValidInputTest3_thenReturns200() throws Exception {
        // Given
        when(priceServicePort.findByBrandProductBetweenDate(anyLong(), anyLong(), any()))
                .thenReturn(Price.builder().build());
        when(priceMapper.toResponseDto(any()))
                .thenReturn(mocks.createPriceResponse3());

        // When & Then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .param("dateQuery", "2020-06-14T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(30.5))
                .andExpect(jsonPath("$.startDate").value("2020-06-15 00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15 11:00:00"));
    }

    @Test
    void whenValidInputTest4_thenReturns200() throws Exception {
        // Given
        when(priceServicePort.findByBrandProductBetweenDate(anyLong(), anyLong(), any()))
                .thenReturn(Price.builder().build());
        when(priceMapper.toResponseDto(any()))
                .thenReturn(mocks.createPriceResponse4());

        // When & Then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .param("dateQuery", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.startDate").value("2020-06-15 16:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31 23:59:59"));
    }


    @Test
    void whenPriceNotFound_thenReturns404() throws Exception {
        // Given
        var dateTest = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

        willThrow(new PriceNotFoundException(8L, 35455L, dateTest))
                .given(priceServicePort).findByBrandProductBetweenDate(anyLong(), anyLong(), any());

        // When & Then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "8"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Price Not Found"))
                .andExpect(jsonPath("$.message").value("Price not found for brand ID: 8, product ID: 35455, date: 2020-06-14T10:00"));
    }
}
