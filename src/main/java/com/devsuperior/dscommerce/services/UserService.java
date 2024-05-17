package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<UserDetailsProjection> users = repository.searchUserAndRolesByEmail(username);
    if(users.isEmpty()) {
      throw new UsernameNotFoundException("Email not found");
    }
    User user = new User();
    user.setEmail(username);
    user.setPassword(users.get(0).getPassword());
    for(UserDetailsProjection userDetails : users) {
      user.addRole(new Role(userDetails.getRoleId(), userDetails.getAuthority()));
    }
    return user;
  }

  protected User authenticated() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
      String username = jwtPrincipal.getClaim("username");
      return repository.findByEmail(username).get();
    } catch (Exception e) {
      throw new UsernameNotFoundException("Email not found");
    }
  }

  @Transactional
  public UserDTO getMe() {
    User user = authenticated();
    return toDTO(user);
  }

  private UserDTO toDTO(User user) {
    return new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPhone(),
        user.getBirthDate(),
        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
    );
  }
}
