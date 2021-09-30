package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface RoutineService {
    List<RoutinePlan> getRoutines();

    RoutinePlan getRoutineById(Long id);

    WeeklyRoutinePlan getWeeklyRoutine(Integer weekDay);

    FrequencyRoutinePlan getFrequencyRoutine(Integer index);

    IndividualRoutinePlan getIndividualRoutine(Date date);

    void updateRoutine(RoutinePlan routinePlan);

    RoutinePlan createRoutine(RoutinePlan routinePlan);

    WeeklyRoutinePlan setWeeklyRoutine(Integer weekDay, Routine routine);

    FrequencyRoutinePlan setFrequencyRoutine(Integer index);

    IndividualRoutinePlan setIndividualRoutine(Date date);

    void deleteRoutine(Long id);
}
