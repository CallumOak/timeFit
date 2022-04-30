package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.repository.RoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoutineServiceImpl implements RoutineService {
    private final RoutineRepository routineRepository;
    private final UserService userService;

    @Override
    public Set<Routine> getRoutines(String userName) {
        return null;
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
    public void updateRoutine(Routine routine, String username) {
        Optional<Routine> oldRoutine = routineRepository.findById(routine.getId());
        if(oldRoutine.isPresent() && Objects.equals(oldRoutine.get().getUser().getUsername(), username)){
            routineRepository.save(routine);
        }
    }

    @Override
    public void deleteRoutine(Long id, String username) {
        if(routineRepository.findById(id).isPresent() &&
                Objects.equals(routineRepository.findById(id).get().getUser().getUsername(), username)){
            routineRepository.deleteById(id);
        }
    }
}
