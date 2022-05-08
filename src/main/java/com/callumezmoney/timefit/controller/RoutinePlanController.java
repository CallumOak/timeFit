package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.mapper.RoutinePlanMapper;
import com.callumezmoney.timefit.model.RoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${callumezmoney.app.webapiprefix.routineplan}")
@AllArgsConstructor
@Api(value = "RoutinePlan API")
public class RoutinePlanController {

    private final RoutinePlanService routinePlanService;
    private final RoutinePlanMapper routinePlanMapper;
/*
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
*/
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoutinePlanById(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(routinePlanMapper.entityToDto(routinePlanService.getRoutinePlan(id, principal.getName()).get()));
    }

    @GetMapping("")
    public ResponseEntity<?> listRoutinePlans(Principal principal){
        return ResponseEntity.ok(
                routinePlanService.getRoutines()
                .stream().map(routinePlanMapper::entityToDto).collect(Collectors.toList())
        );
    }

    @PostMapping ("")
    public ResponseEntity<?> addRoutinePlan(@RequestBody RoutinePlan routinePlan, Principal principal){
        return ResponseEntity.ok(routinePlanMapper.entityToDto(routinePlanService.createRoutinePlan(routinePlan, principal.getName()).get()));
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editRoutinePlan(@RequestBody RoutinePlan routinePlan, Principal principal){
        routinePlanService.editRoutinePlan(routinePlan, principal.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoutinePlan(@PathVariable Long id, Principal principal){
        routinePlanService.deleteRoutinePlan(id, principal.getName());
    }


}
