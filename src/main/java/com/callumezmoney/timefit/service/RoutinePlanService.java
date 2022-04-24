package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface RoutinePlanService {
    List<RoutinePlan> getRoutines();

    RoutinePlan getRoutinePlanById(Long id);

    WeeklyRoutinePlan getWeeklyRoutine(Long id);

    FrequencyRoutinePlan getFrequencyRoutine(Long id);

    IndividualRoutinePlan getIndividualRoutine(Long id);

    void updateRoutine(RoutinePlan routinePlan);

    RoutinePlan createRoutine(RoutinePlan routinePlan);

    WeeklyRoutinePlan setWeeklyRoutine(Integer weekDay, Routine routine);

    FrequencyRoutinePlan setFrequencyRoutine(Integer index);

    IndividualRoutinePlan setIndividualRoutine(Date date);

    void deleteRoutine(Long id);
}
