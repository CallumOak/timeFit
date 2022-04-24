package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

public interface ExerciseService {
    //TODO managing access for different use cases such as admin listall, user adding from marketplace, etc.
    Set<Exercise> getAllExercises(String username);
    Optional<Exercise> getExercise(Long id, String username);
    Optional<Exercise> getExercise(Long id);
    Optional<Exercise> addExercise(Exercise exercise, String username);
    void editExercise(Exercise exercise, String username);
    void deleteExercise(Long id, String username);
}
