package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
