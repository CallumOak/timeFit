package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.RoutineDTO;
import com.callumezmoney.timefit.model.Routine;

import java.util.List;
import java.util.Optional;

public interface RoutineService {
    List<Routine> getRoutines(String userName);
    Optional<Routine> getRoutine(Long id, String username);
    Optional<Routine> getRoutine(Long id);
    Optional<Routine> createRoutine(Routine routine, String username);
    void updateRoutine(RoutineDTO routine, String username);
    void deleteRoutine(Long id, String username);
}
