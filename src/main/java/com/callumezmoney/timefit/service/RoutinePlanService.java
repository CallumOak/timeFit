package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.FrequencyRoutinePlanDTO;
import com.callumezmoney.timefit.dto.IndividualRoutinePlanDTO;
import com.callumezmoney.timefit.dto.WeeklyRoutinePlanDTO;
import com.callumezmoney.timefit.model.*;

import java.util.List;
import java.util.Optional;

public interface RoutinePlanService {
    List<WeeklyRoutinePlan> getWeeklyRoutinePlans();
    List<FrequencyRoutinePlan> getFrequencyRoutinePlans();
    List<IndividualRoutinePlan> getIndividualRoutinePlans();
    Optional<WeeklyRoutinePlan> getWeeklyRoutinePlan(Long id, String username);

    Optional<FrequencyRoutinePlan> getFrequencyRoutinePlan(Long id, String username);

    Optional<IndividualRoutinePlan> getIndividualRoutinePlan(Long id, String username);

    WeeklyRoutinePlan getWeeklyRoutinePlan(Long id);

    FrequencyRoutinePlan getFrequencyRoutinePlan(Long id);

    IndividualRoutinePlan getIndividualRoutinePlan(Long id);

    void editRoutinePlan(WeeklyRoutinePlanDTO routinePlan, String username);

    void editRoutinePlan(FrequencyRoutinePlanDTO routinePlan, String username);

    void editRoutinePlan(IndividualRoutinePlanDTO routinePlan, String username);

    Optional<WeeklyRoutinePlan> createRoutinePlan(WeeklyRoutinePlan routinePlan, String username);

    Optional<FrequencyRoutinePlan> createRoutinePlan(FrequencyRoutinePlan routinePlan, String username);

    Optional<IndividualRoutinePlan> createRoutinePlan(IndividualRoutinePlan routinePlan, String username);

    void deleteRoutinePlan(Long id, String username);
}
