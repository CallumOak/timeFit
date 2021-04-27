package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ExerciseService {
    Set<Exercise> getExercises();
}
