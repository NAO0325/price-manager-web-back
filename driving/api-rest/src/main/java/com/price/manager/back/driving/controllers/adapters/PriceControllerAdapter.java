package com.price.manager.back.driving.controllers.adapters;


import com.price.manager.back.application.ports.driving.PriceServicePort;
import com.price.manager.back.driving.controllers.models.ErrorResponse;
import com.price.manager.back.driving.controllers.models.PriceResponse;
import com.price.manager.back.driving.controllers.mappers.PriceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/catalogue")
@Tag(name = "Price Controller", description = "API to manage prices")
public class PriceControllerAdapter {

    private final PriceServicePort priceServicePort;

    private final PriceMapper mapper;

    @Operation(
            summary = "Get a Price by its brandId, productId and offer date",
            description = "Returns the applicable price based on brand, product and query date"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Price successfully found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameters supplied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Price not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/price/findByBrandProductBetweenDate")
    public ResponseEntity<?> findByBrandProductBetweenDate(
            @NotNull(message = "brandId must not be null")
            @RequestParam Long brandId,

            @NotNull(message = "productId must not be null")
            @RequestParam Long productId,

            @NotNull(message = "dateQuery must not be null")
            @RequestParam LocalDateTime dateQuery
    ) {

        var price = priceServicePort.findByBrandProductBetweenDate(brandId, productId, dateQuery);

        var response = mapper.toResponseDto(price);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

