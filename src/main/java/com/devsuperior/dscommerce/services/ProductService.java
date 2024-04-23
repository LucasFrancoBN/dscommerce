package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Product product = productRepository.findById(id).get();
    return toDTO(product);
  }

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAll(Pageable pageable) {
    Page<Product> products = productRepository.findAll(pageable);
    return products.map(this::toDTO);
  }

  private ProductDTO toDTO(Product product) {
    return new ProductDTO(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getImgUrl()
    );
  }
}
