package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.service.RoutineService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/routine/")
@AllArgsConstructor
@Api(value = "Routine API")
public class RoutineController {
    RoutineService routineService;

    @GetMapping
    public ResponseEntity<?> getRoutines(Principal principal) {
        return ResponseEntity.ok(routineService.getRoutines(principal.getName()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getRoutine(@PathVariable int id, Principal principal) {
        Routine exercise = routineService.getRoutine(id, principal.getName());
        if(exercise != null){
            return ResponseEntity.ok(exercise);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Routine not found"));
    }

    @PostMapping
    public ResponseEntity<?> addRoutine(@RequestBody Routine routine, Principal principal){
        return ResponseEntity.ok(routineService.createRoutine(routine, principal.getName()));
    }

    @PutMapping
    public void editRoutine(@RequestBody Routine routine, Principal principal){
        routineService.updateRoutine(routine, principal.getName());
    }

    @DeleteMapping("{id}")
    public void deleteRoutine(@PathVariable int id, Principal principal){
        routineService.deleteRoutine(id, principal.getName());
    }
}
