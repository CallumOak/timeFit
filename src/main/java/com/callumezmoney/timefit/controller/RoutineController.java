package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.RoutinePlan;
import com.callumezmoney.timefit.repository.*;
import com.callumezmoney.timefit.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RoutineController {

    private final RoutineService routineService;


    public RoutineController(RoutineService service){
        this.routineService = service;
    }

    @GetMapping("/routines/{type}/{identifier}")
    public ResponseEntity<RoutinePlan> getRoutineByType(@PathVariable String type, @PathVariable String identifier){
        RoutinePlan routinePlan;
        switch (type){
            case "weekly":
                routinePlan = routineService.getWeeklyRoutine(Integer.getInteger(identifier));
                return ResponseEntity.ok().body(routinePlan);
            case "frequency":
                routinePlan = routineService.getFrequencyRoutine(Integer.getInteger(identifier));
                return ResponseEntity.ok().body(routinePlan);
            case "individual":
                try {
                    routinePlan = routineService.getIndividualRoutine(DateFormat.getDateInstance().parse(identifier));
                    return ResponseEntity.ok().body(routinePlan);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Exception e = new TypeNotPresentException("Type not recognised : " + type, null);
                e.printStackTrace();
                return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/routines/{id}")
    public RoutinePlan getRoutineById(@PathVariable Long id, Principal user){

        return routineService.getRoutineById(id);
    }

    @GetMapping("/routines")
    public List<RoutinePlan> listRoutines(){
        return routineService.getRoutines();
    }

    @PostMapping ("/routines")
    public RoutinePlan addRoutine(@RequestBody RoutinePlan routinePlan){
        return routineService.createRoutine(routinePlan);
    }

    @PutMapping("/routines")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editRoutine(@RequestBody RoutinePlan routinePlan){
        routineService.updateRoutine(routinePlan);
    }

    @DeleteMapping("/routines/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoutine(@PathVariable Long id){
        routineService.deleteRoutine(id);
    }


}
