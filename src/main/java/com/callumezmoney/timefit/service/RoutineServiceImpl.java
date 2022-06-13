package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.RoutineDTO;
import com.callumezmoney.timefit.mapper.ExerciseMapper;
import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.repository.ExercisesRepository;
import com.callumezmoney.timefit.repository.RoutineRepository;
import com.callumezmoney.timefit.util.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoutineServiceImpl implements RoutineService {
    private Environment environment;
    private final RoutineRepository routineRepository;
    private final UserService userService;
    private ExercisesRepository exercisesRepository;

    @Override
    public List<Routine> getRoutines(String username) {
        return userService.getUser(username).isPresent() ?
                routineRepository.findAllByUser(userService.getUser(username).get()) : new ArrayList<>();
    }

    @Override
    public Optional<Routine> getRoutine(Long id, String username) {
        return userService.getUser(username).isPresent() ?
                routineRepository.findByIdAndUser(id, userService.getUser(username).get()) : Optional.empty();
    }

    @Override
    public Optional<Routine> getRoutine(Long id) {
        return routineRepository.findById(id);
    }

    @Override
    public Optional<Routine> createRoutine(Routine routine, String username) {
        return Objects.equals(routine.getUser().getUsername(), username) ?
                Optional.of(routineRepository.save(routine)) : Optional.empty();
    }

    @Override
    @Transactional
    public void updateRoutine(RoutineDTO routine, String username) {
        Optional<Routine> oPersistedRoutine = routineRepository.findById(routine.getId());
        if(oPersistedRoutine.isPresent() && Objects.equals(oPersistedRoutine.get().getUser().getUsername(), username)){
            Routine persistedRoutine = oPersistedRoutine.get();
            persistedRoutine.setName(routine.getName());
            persistedRoutine.setColor(routine.getColor());
            List<Exercise> exerciseList = routine.getExercises().stream().map(
                    eUrl -> exercisesRepository.findById(MapperUtils.getIdFromURI(eUrl, environment, "exercise")).orElse(null)
                ).collect(Collectors.toList());
            persistedRoutine.setExercises(exerciseList, routine.getExercisePositions());
        }
    }

    @Override
    public void deleteRoutine(Long id, String username) {
        Optional<Routine> routine = routineRepository.findById(id);
        if(routine.isPresent() &&
                Objects.equals(routine.get().getUser().getUsername(), username)){
            routine.get().remove();
            routineRepository.deleteById(id);
        }
    }
}
