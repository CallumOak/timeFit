package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("api/exercise/")
@AllArgsConstructor
public class ExerciseController {
    ExercisesRepository exercisesRepository;

    @GetMapping("{id}")
    public ResponseEntity<?> getExercise(@PathVariable Long id){
        Optional<Exercise> exercise = exercisesRepository.findById(id);
        if(exercise.isPresent()){
            return ResponseEntity.ok(exercise.get());
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Exercise not found"));
    }

    @GetMapping()
    public ResponseEntity<?> listExercise(){
        return ResponseEntity.ok(exercisesRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> addExercise(@RequestBody Exercise newExercise){
        return ResponseEntity.ok(exercisesRepository.save(newExercise));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editExercise(@RequestBody Exercise newExercise){
        Optional<Exercise> exercise = exercisesRepository.findById(newExercise.getId());
        if(exercise.isPresent()){
            exercisesRepository.save(newExercise);
        }
        else{
            throw new NullPointerException();
        }
    }

    @DeleteMapping("{id}")
    public void deleteExercise(@PathVariable Long id){
        exercisesRepository.deleteById(id);
    }
}
