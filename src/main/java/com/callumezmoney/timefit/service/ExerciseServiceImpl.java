package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExercisesRepository exercisesRepository;
    private final UserService userService;

    @Override
    public Set<Exercise> getAllExercises(String username) {
        return exercisesRepository.findAll();
    }

    @Override
    public Optional<Exercise> getExercise(Long id, String username) {
        return exercisesRepository.findByIdAndUser(id, userService.getUser(username).get());
    }

    @Override
    public Optional<Exercise> addExercise(Exercise exercise, String username) {
        Optional<Exercise> optionalExercise = Optional.empty();
        if(exercise.getUser().getUsername() == username){
            optionalExercise.of(exercisesRepository.save(exercise));
        }
        return optionalExercise;
    }

    @Override
    public void editExercise(Exercise exercise, String username) {
        if(exercise.getUser().getUsername() == username){
            exercisesRepository.save(exercise);
        }
    }

    @Override
    public void deleteExercise(Long id, String username) {
        Optional<Exercise> optionalExercise = exercisesRepository.findById(id);
        if(optionalExercise.isPresent() && optionalExercise.get().getUser().getUsername() == username){
            exercisesRepository.deleteById(id);
        }
    }
}
