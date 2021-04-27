package com.callumezmoney.timefit.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    public Model getUser(Model model){

        return model;
    }
    public Model createUser(Model model){

        return model;
    }
    public Model editUser(Model model){

        return model;
    }
    public void deleteUser(Model model){

    }
}
