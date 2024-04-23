package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
    ProductDTO productDTO = productService.findById(id);
    return ResponseEntity.ok(productDTO);
  }

  @GetMapping
  public ResponseEntity<Page<ProductDTO>> findAll(
      @PageableDefault(size = 12, page = 0, sort = "name") Pageable pageable
  ) {
    Page<ProductDTO> productDTOPage = productService.findAll(pageable);
    return ResponseEntity.ok(productDTOPage);
  }

  @PostMapping
  public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO) {
    ProductDTO productSaved = productService.insert(productDTO);
    URI uri = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(productSaved.id())
        .toUri();
    return ResponseEntity.created(uri).body(productSaved);
  }
}
