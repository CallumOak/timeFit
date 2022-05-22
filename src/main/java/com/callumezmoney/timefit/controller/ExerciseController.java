package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.dto.ExerciseDTO;
import com.callumezmoney.timefit.mapper.ExerciseMapper;
import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.service.ExerciseService;
import com.callumezmoney.timefit.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${callumezmoney.app.webapiprefix.exercise}")
@AllArgsConstructor
@Api(value = "Exercise API")
public class ExerciseController {
    ExerciseService exercisesService;
    ExerciseMapper exerciseMapper;
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> listExercise(Principal principal){
        List<Exercise> exercises = exercisesService.getAllExercises(principal.getName());
        return ResponseEntity.ok(exercises.stream()
                .map(exerciseMapper::entityToDto)
                .collect(Collectors.toList()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExercise(@PathVariable Long id, Principal principal){
        Optional<Exercise> exercise = exercisesService.getExercise(id, principal.getName());
        if(exercise.isPresent()){
            return ResponseEntity.ok(exerciseMapper.entityToDto(exercise.get()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Exercise not found"));
    }

    @PostMapping()
    public ResponseEntity<?> addExercise(@RequestBody ExerciseDTO newExerciseDto, Principal principal){
        Exercise newExercise = exerciseMapper.dtoToEntity(newExerciseDto);
        User user = userService.getUser(principal.getName()).get();
        newExercise.setUser(user);
        Optional<Exercise> exercise = exercisesService.addExercise(newExercise, principal.getName());
        return ResponseEntity.ok(exerciseMapper.entityToDto(exercise.get()));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editExercise(@RequestBody ExerciseDTO newExerciseDto, Principal principal){
        exercisesService.editExercise(newExerciseDto, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable Long id, Principal principal){
        exercisesService.deleteExercise(id, principal.getName());
    }
}
