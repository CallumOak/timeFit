package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUser(String email, String password) {
        ArrayList<User> users = (ArrayList) userRepository.findAll();
        User desiredUser = null;
        for(User user : users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                desiredUser = user;
                break;
            }
        }

        return desiredUser;
    }
}
