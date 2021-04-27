package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import com.callumezmoney.timefit.repository.RoutineRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final UserRepository userRepository;
    private final ExercisesRepository exercisesRepository;
    @Override
    public Set<Exercise> getExercises() {
        Set<Exercise> exercises = (Set<Exercise>) exercisesRepository.findAll();
        return exercises;
    }
}
