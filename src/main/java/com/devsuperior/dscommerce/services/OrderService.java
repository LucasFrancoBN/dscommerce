package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ClientDTO;
import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.dto.PaymentDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class OrderService {
  @Autowired
  private OrderRepository repository;

  @Transactional(readOnly = true)
  public OrderDTO findById(Long id) {
    Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    return toDTO(order);
  }

  private OrderDTO toDTO(Order order) {
    return new OrderDTO(
        order.getId(),
        order.getMoment(),
        order.getStatus(),
        new ClientDTO(order.getClient().getId(), order.getClient().getName()),
        order.getPayment() == null ? null : new PaymentDTO(order.getId(), order.getMoment()),
        order.getOrderItems().stream().map(orderItem -> new OrderItemDTO(
            orderItem.getOrder().getId(),
            orderItem.getProduct().getName(),
            orderItem.getPrice(),
            orderItem.getQuantity()
        )).toList()
    );
  }

}
