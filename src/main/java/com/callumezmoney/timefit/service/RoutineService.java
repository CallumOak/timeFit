package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Routine;

import java.util.Set;

public interface RoutineService {
    Set<Routine> getRoutines(String userName);
    Routine getRoutine(Long id, String username);
    Routine getRoutine(Long id);
    Routine createRoutine(Routine routine, String username);
    void updateRoutine(Routine routine, String username);
    void deleteRoutine(Long id, String username);
}
