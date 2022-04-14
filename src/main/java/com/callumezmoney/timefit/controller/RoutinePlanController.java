package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.RoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/routinePlan/")
@AllArgsConstructor
@Api(value = "RoutinePlan API")
public class RoutinePlanController {

    private final RoutinePlanService routinePlanService;

    @GetMapping("{type}/{identifier}")
    public ResponseEntity<RoutinePlan> getRoutinePlanByType(@PathVariable String type, @PathVariable String identifier){
        RoutinePlan routinePlan;
        switch (type){
            case "weekly":
                routinePlan = routinePlanService.getWeeklyRoutine(Integer.getInteger(identifier));
                return ResponseEntity.ok().body(routinePlan);
            case "frequency":
                routinePlan = routinePlanService.getFrequencyRoutine(Integer.getInteger(identifier));
                return ResponseEntity.ok().body(routinePlan);
            case "individual":
                try {
                    routinePlan = routinePlanService.getIndividualRoutine(DateFormat.getDateInstance().parse(identifier));
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

    @GetMapping("{id}")
    public RoutinePlan getRoutinePlanById(@PathVariable Long id, Principal user){
        return routinePlanService.getRoutinePlanById(id);
    }

    @GetMapping("")
    public List<RoutinePlan> listRoutinePlans(){
        return routinePlanService.getRoutines();
    }

    @PostMapping ("")
    public RoutinePlan addRoutinePlan(@RequestBody RoutinePlan routinePlan){
        return routinePlanService.createRoutine(routinePlan);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editRoutinePlan(@RequestBody RoutinePlan routinePlan){
        routinePlanService.updateRoutine(routinePlan);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoutinePlan(@PathVariable Long id){
        routinePlanService.deleteRoutine(id);
    }


}
