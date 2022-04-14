package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> getUser(String email, String password);
    Optional<User> getUser(String username);
    Optional<User> getUser(Long id);
    Set<User> getAll();
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
}
