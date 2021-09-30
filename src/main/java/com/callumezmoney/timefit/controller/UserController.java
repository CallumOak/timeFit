package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.repository.UserRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RequestMapping("api/user/")
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found"));
    }

    @GetMapping()
    public ResponseEntity<?> listUser(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User newUser){
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editUser(@RequestBody User newUser){
        Optional<User> user = userRepository.findById(newUser.getId());
        if(user.isPresent()){
            userRepository.save(newUser);
        }
        else{
            throw new NullPointerException();
        }
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }
}
