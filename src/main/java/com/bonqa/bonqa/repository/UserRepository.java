package com.bonqa.bonqa.repository;

import com.bonqa.bonqa.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    void deleteById(UUID userId);

    User findById(UUID userId);
}
