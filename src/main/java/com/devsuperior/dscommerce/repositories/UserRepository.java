package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(nativeQuery = true, value = """
        SELECT u.email, u.password, r.id, r.authority FROM tb_user u
        INNER JOIN tb_user_role ur ON ur.user_id = u.id
        INNER JOIN tb_role r ON r.id = ur.role_id 
        WHERE u.email = :email
    """)
  List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
