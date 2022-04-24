package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.repository.RoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class RoutineServiceImpl implements RoutineService {
    private final RoutineRepository routineRepository;

    @Override
    public Set<Routine> getRoutines(String userName) {
        return null;
    }

    @Override
    public Routine getRoutine(Long id, String username) {
        return null;
    }

    @Override
    public Routine getRoutine(Long id) {
        return null;
    }

    @Override
    public Routine createRoutine(Routine routine, String username) {
        return null;
    }

    @Override
    public void updateRoutine(Routine routine, String username) {

    }

    @Override
    public void deleteRoutine(Long id, String username) {

    }
}
