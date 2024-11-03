package com.price.manager.back.driving.controllers.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Error Response Structure")
public class ErrorResponse {

    @Schema(description = "HTTP Status code", example = "400")
    private int status;

    @Schema(description = "Error type or source", example = "Validation Error")
    private String error;

    @Schema(description = "Detailed error message",
            example = "Invalid format for: The value '123abc' is not a valid number")
    private String message;
}