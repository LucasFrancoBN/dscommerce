package com.devsuperior.dscommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductDTO(
    Long id,
    @Size(min = 3, max = 80, message = "Name field must be 3 to 80 characters long")
    @NotBlank(message = "Required field")
    String name,
    @Size(min = 10, message = "Description field must have at least 10 characters")
    @NotBlank(message = "Required field")
    String description,
    @Positive(message = "Price must be positive value")
    Double price,
    String imgUrl
) {
}
