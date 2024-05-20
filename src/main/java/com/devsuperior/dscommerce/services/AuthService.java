package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired
  private UserService service;

  public void validateSelfOrAdmin(Long userId) {
    User me = service.authenticated();
    if (!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)) {
      throw new ForbiddenException("Access denied");
    }
  }
}
