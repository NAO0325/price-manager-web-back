package com.price.manager.back.driving.controllers.adapters;

import com.price.manager.back.application.exceptions.PriceNotFoundException;
import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.domain.Price;
import com.price.manager.back.driving.controllers.error.CustomExceptionHandler;
import com.price.manager.back.driving.controllers.mappers.PriceMapper;
import com.price.manager.back.driving.controllers.models.PriceResponse;
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

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceMapper priceMapper;

    @MockBean
    private PriceServicePort priceServicePort;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        // Given
        when(priceServicePort.findByBrandProductBetweenDate(anyLong(), anyLong(), any()))
                .thenReturn(Price.builder().build());
        when(priceMapper.toResponseDto(any()))
                .thenReturn(PriceResponse.builder().build());

        // When & Then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void whenPriceNotFound_thenReturns404() throws Exception {
        // Given
        var dateTest = LocalDateTime.of(2020,6,14, 10, 0, 0);

        willThrow(new PriceNotFoundException(1L, 35455L, dateTest))
                .given(priceServicePort).findByBrandProductBetweenDate(anyLong(), anyLong(), any());

        // When & Then
        mvc.perform(get("/api/catalogue/price/findByBrandProductBetweenDate")
                        .param("dateQuery", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Price Not Found"))
                .andExpect(jsonPath("$.message").value("Price not found for brand ID: 1, product ID: 35455, date: 2020-06-14T10:00"));
    }
}
