package com.callumezmoney.timefit.repository;

import com.callumezmoney.timefit.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndPassword(String email, String password);

    void deleteById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
