package com.bonqa.bonqa.domain.repository;

import com.bonqa.bonqa.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findOneByUsername(String username);

}