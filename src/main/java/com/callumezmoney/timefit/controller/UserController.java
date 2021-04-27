package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/users")
    void addUser(@RequestBody User user) {
        userRepository.save(user);
    }
//    public Model getUser(Model model){
//
//        return model;
//    }
//    public Model createUser(Model model){
//
//        return model;
//    }
//    public Model editUser(Model model){
//
//        return model;
//    }
//    public void deleteUser(Model model){
//
//    }
}
