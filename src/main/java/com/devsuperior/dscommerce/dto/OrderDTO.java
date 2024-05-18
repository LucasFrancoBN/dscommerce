package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.List;

public record OrderDTO(
    Long id,
    Instant moment,
    OrderStatus status,
    ClientDTO client,
    PaymentDTO payment,
    @NotEmpty(message = "Deve ter pelo menos um item")
    List<OrderItemDTO> items
) {
  public Double getTotal() {
    return items.stream().mapToDouble(OrderItemDTO::getSubtotal).sum();
  }
}
