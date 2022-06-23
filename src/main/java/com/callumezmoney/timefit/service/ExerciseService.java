package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.ExerciseDTO;
import com.callumezmoney.timefit.model.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    //TODO managing access for different use cases such as admin listall, user adding from marketplace, etc.
    List<Exercise> getAllExercises(String username);
    Optional<Exercise> getExercise(Long id, String username);
    Optional<Exercise> getExercise(Long id);
    Optional<Exercise> addExercise(Exercise exercise, String username);
    void editExercise(ExerciseDTO exercise, String username);
    void deleteExercise(Long id, String username);
}
