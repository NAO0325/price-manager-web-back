package com.price.manager.back.driving.controllers.adapters;


import com.price.manager.back.application.ports.driving.PriceServicePort;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Price not found",
                    content = @Content
            )
    })
    @GetMapping("/price/findByBrandProductBetweenDate")
    public ResponseEntity<PriceResponse> findByBrandProductBetweenDate(
            @NotNull(message = "brandId must not be null")
            @RequestParam String brandId,

            @NotNull(message = "productId must not be null")
            @RequestParam String productId,

            @NotNull(message = "dateQuery must not be null")
            @RequestParam String dateQuery
    ) {
        PriceResponse response = mapper.toResponseDto(
                priceServicePort.findByBrandProductBetweenDate(brandId, productId, dateQuery)
        );

        return (response != null) ?
                new ResponseEntity<>(response, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
