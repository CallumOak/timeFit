package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Routine;

import java.util.Set;

public interface RoutineService {
    Set<Routine> getRoutines(String userName);
    Routine getRoutine(int id, String username);
    Routine createRoutine(Routine routine, String username);
    void updateRoutine(Routine routine, String username);
    void deleteRoutine(int id, String username);
}
