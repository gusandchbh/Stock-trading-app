package com.bonqa.bonqa.domain.repository;

import com.bonqa.bonqa.domain.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String username);

  Optional<User> findByEmail(String email);
}
