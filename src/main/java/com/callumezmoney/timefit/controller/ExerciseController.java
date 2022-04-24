package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import com.callumezmoney.timefit.service.ExerciseService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("api/exercise")
@AllArgsConstructor
@Api(value = "Exercise API")
public class ExerciseController {
    ExerciseService exercisesService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getExercise(@PathVariable Long id, Principal principal){
        Optional<Exercise> exercise = exercisesService.getExercise(id, principal.getName());
        if(exercise.isPresent()){
            return ResponseEntity.ok(exercise.get());
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Exercise not found"));
    }

    @GetMapping()
    public ResponseEntity<?> listExercise(Principal principal){
        return ResponseEntity.ok(exercisesService.getAllExercises(principal.getName()));
    }

    @PostMapping()
    public ResponseEntity<?> addExercise(@RequestBody Exercise newExercise, Principal principal){
        return ResponseEntity.ok(exercisesService.addExercise(newExercise, principal.getName()));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editExercise(@RequestBody Exercise newExercise, Principal principal){
        Optional<Exercise> exercise = exercisesService.getExercise(newExercise.getId(), principal.getName());
        if(exercise.isPresent()){
            exercisesService.editExercise(newExercise, principal.getName());
        }
        else{
            throw new NullPointerException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable Long id, Principal principal){
        exercisesService.deleteExercise(id, principal.getName());
    }
}
