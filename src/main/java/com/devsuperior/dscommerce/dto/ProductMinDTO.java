package com.devsuperior.dscommerce.dto;

public record ProductMinDTO(
    Long id,
    String name,
    Double price,
    String imgUrl
) {
}
