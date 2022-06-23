package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.ExerciseDTO;
import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExercisesRepository exercisesRepository;
    private final UserService userService;

    @Override
    public List<Exercise> getAllExercises(String username) {
        return exercisesRepository.findAll().stream()
                .filter(e -> e.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Exercise> getExercise(Long id, String username) {
        return userService.getUser(username).isPresent() ?
                exercisesRepository.findByIdAndUser(id, userService.getUser(username).get()) : Optional.empty();
    }

    @Override
    public Optional<Exercise> getExercise(Long id) {
        return exercisesRepository.findById(id);
    }

    @Override
    public Optional<Exercise> addExercise(Exercise exercise, String username) {
        return Objects.equals(exercise.getUser().getUsername(), username) ?
            Optional.of(exercisesRepository.save(exercise)) : Optional.empty();
    }

    @Override
    @Transactional
    public void editExercise(ExerciseDTO exercise, String username) {
        Optional<Exercise> oldExercise = exercisesRepository.findById(exercise.getId());
        if(oldExercise.isPresent() && Objects.equals(oldExercise.get().getUser().getUsername(), username)){
            Exercise editedExercise = oldExercise.get();
            editedExercise.setName(exercise.getName());
            editedExercise.setExerciseDuration(exercise.getExerciseDuration());
            editedExercise.setExerciseBreak(exercise.getExerciseBreak());
            editedExercise.setRepetitions(exercise.getRepetitions());
            editedExercise.setExerciseColor(exercise.getExerciseColor());
            editedExercise.setBreakColor(exercise.getBreakColor());
            editedExercise.setIllustrationLocation(exercise.getIllustrationLocation());
            editedExercise.setExerciseSoundLocation(exercise.getExerciseSoundLocation());
            editedExercise.setBreakSoundLocation(exercise.getBreakSoundLocation());
            editedExercise.setCountdownSoundLocation(exercise.getCountdownSoundLocation());
        }
    }

    @Override
    public void deleteExercise(Long id, String username) {
        Optional<Exercise> exercise = exercisesRepository.findById(id);
        if(exercise.isPresent() && Objects.equals(exercise.get().getUser().getUsername(), username)){
            exercisesRepository.deleteById(id);
        }
    }
}
