package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
  @Autowired
  OrderService service;

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
  public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
    OrderDTO order =  service.findById(id);
    return ResponseEntity.ok().body(order);
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_CLIENT')")
  public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO dto) {
    OrderDTO savedDTO = service.insert(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedDTO.id()).toUri();
    return ResponseEntity.created(uri).body(savedDTO);
  }
}
