package com.devsuperior.dscommerce.dto;

import java.time.Instant;

public record PaymentDTO(
    Long id,
    Instant moment
) {
}
