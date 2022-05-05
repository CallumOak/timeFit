package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email, String password);
    Optional<User> getUser(String username);
    Optional<User> getUser(Long id);
    List<User> getAll();
    User addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}
