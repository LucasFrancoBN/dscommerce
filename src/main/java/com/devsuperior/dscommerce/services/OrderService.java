package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ClientDTO;
import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.dto.PaymentDTO;
import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {
  @Autowired
  private OrderRepository repository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private AuthService authService;

  @Transactional(readOnly = true)
  public OrderDTO findById(Long id) {
    Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    authService.validateSelfOrAdmin(order.getClient().getId());
    return toDTO(order);
  }

  @Transactional
  public OrderDTO insert(OrderDTO dto) {
    Order order = new Order();

    order.setMoment(Instant.now());
    order.setStatus(OrderStatus.WAITING_PAYMENT);

    User user = userService.authenticated();
    order.setClient(user);


    for(OrderItemDTO orderItemDTO : dto.items()) {
      Product product = productRepository.getReferenceById(orderItemDTO.id());
      order.getOrderItems().add(new OrderItem(order, product, orderItemDTO.quantity(), product.getPrice()));
    }

    repository.save(order);
    orderItemRepository.saveAll(order.getOrderItems());

    System.out.println(toDTO(order));

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
            orderItem.getQuantity(),
            orderItem.getProduct().getImgUrl()
        )).toList()
    );
  }
}
