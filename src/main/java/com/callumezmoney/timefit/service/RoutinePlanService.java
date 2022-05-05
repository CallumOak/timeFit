package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RoutinePlanService {
    List<RoutinePlan> getRoutines();

    Optional<RoutinePlan> getRoutinePlan(Long id);

    Optional<RoutinePlan> getRoutinePlan(Long id, String username);

    WeeklyRoutinePlan getWeeklyRoutinePlan(Long id);

    FrequencyRoutinePlan getFrequencyRoutinePlan(Long id);

    IndividualRoutinePlan getIndividualRoutinePlan(Long id);

    void editRoutinePlan(RoutinePlan routinePlan, String username);

    Optional<RoutinePlan> createRoutinePlan(RoutinePlan routinePlan, String username);

    WeeklyRoutinePlan editWeeklyRoutinePlan(Integer weekDay, Routine routine);

    FrequencyRoutinePlan editFrequencyRoutinePlan(Integer index);

    IndividualRoutinePlan editIndividualRoutinePlan(Date date);

    void deleteRoutinePlan(Long id, String username);
}
