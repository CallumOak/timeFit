package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.dto.UserCreationDTO;
import com.callumezmoney.timefit.dto.UserDTO;
import com.callumezmoney.timefit.mapper.UserMapper;
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
@RequestMapping( "${callumezmoney.app.webapiprefix.user}")
@AllArgsConstructor
@Api(value = "All APIs")
public class UserController {
    private final UserService userService;
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        Optional<User> user = userService.getUser(id);
        return user.isPresent() ?
                ResponseEntity.ok(userMapper.entityToDto(user.get())) :
                ResponseEntity.badRequest().body(new MessageResponse("Error: User not found"));
    }

    @GetMapping()
    public ResponseEntity<?> listUser(){
        return ResponseEntity.ok(userService.getAll().stream().map(userMapper::entityToDto));
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody UserCreationDTO newUserDto){
        User newUser = userMapper.creationDtoToEntity(newUserDto);
        return ResponseEntity.ok(userMapper.entityToDto(userService.addUser(newUser)));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editUser(@RequestBody UserDTO updatedUserDto){
        User updatedUser = userMapper.dtoToEntity(updatedUserDto);
        Optional<User> user = userService.getUser(updatedUser.getId());
        if(user.isEmpty()){
            throw new NullPointerException();
        }
        userService.updateUser(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
