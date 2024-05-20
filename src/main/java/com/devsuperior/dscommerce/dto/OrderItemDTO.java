package com.devsuperior.dscommerce.dto;

public record OrderItemDTO(
    Long id,
    String name,
    Double price,
    Integer quantity,
    String imgUrl
) {
  public Double getSubtotal() {
    return price * quantity;
  }
}
