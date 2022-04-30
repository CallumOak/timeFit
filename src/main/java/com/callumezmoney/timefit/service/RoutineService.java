package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Routine;

import java.util.Optional;
import java.util.Set;

public interface RoutineService {
    Set<Routine> getRoutines(String userName);
    Optional<Routine> getRoutine(Long id, String username);
    Optional<Routine> getRoutine(Long id);
    Optional<Routine> createRoutine(Routine routine, String username);
    void updateRoutine(Routine routine, String username);
    void deleteRoutine(Long id, String username);
}
