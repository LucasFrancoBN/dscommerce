package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    return toDTO(product);
  }

  @Transactional(readOnly = true)
  public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
    Page<Product> products = productRepository.searchByName(name, pageable);
    return products.map(this::toMinDTO);
  }

  @Transactional
  public ProductDTO insert(ProductDTO productDTO) {
    Product productSaved = productRepository.save(toProduct(productDTO));
    return toDTO(productSaved);
  }

  @Transactional
  public ProductDTO update(Long id, ProductDTO productDTO) {
    try {
      Product product = productRepository.getReferenceById(id);
      copyDtoToEntity(productDTO, product);
      return toDTO(productRepository.save(product));
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Product not found");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if(!productRepository.existsById(id)) {
      throw new ResourceNotFoundException("Product not found");
    }
    try {
      productRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Referential integrity failure");
    }
  }

  private void copyDtoToEntity(ProductDTO productDTO, Product product) {
    product.setName(productDTO.name());
    product.setDescription(productDTO.description());
    product.setPrice(productDTO.price());
    product.setImgUrl(productDTO.imgUrl());
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

  private ProductMinDTO toMinDTO(Product product) {
    return new ProductMinDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getImgUrl()
    );
  }

  private Product toProduct(ProductDTO productDTO) {
    return new Product(
        productDTO.id(),
        productDTO.name(),
        productDTO.description(),
        productDTO.price(),
        productDTO.imgUrl()
    );
  }
}
