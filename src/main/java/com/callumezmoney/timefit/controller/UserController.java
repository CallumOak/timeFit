package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping( "api/user")
@AllArgsConstructor
@Api(value = "All APIs")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        Optional<User> user = userService.getUser(id);
        return user.isPresent() ?
                ResponseEntity.ok(user.get()) :
                ResponseEntity.badRequest().body(new MessageResponse("Error: User not found"));
    }

    @GetMapping()
    public ResponseEntity<?> listUser(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User newUser){
        return ResponseEntity.ok(userService.addUser(newUser));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editUser(@RequestBody User updatedUser){
        Optional<User> user = userService.getUser(updatedUser.getId());
        if(!user.isPresent()){
            throw new NullPointerException();
        }
        userService.updateUser(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
