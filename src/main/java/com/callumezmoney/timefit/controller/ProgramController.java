package com.callumezmoney.timefit.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProgramController {


    public Model getProgram(Model model){

        return model;
    }
    public Model createProgram(Model model){

        return model;
    }
    public Model editProgram(Model model){

        return model;
    }
    public void deleteProgram(Model model){
    }
}
