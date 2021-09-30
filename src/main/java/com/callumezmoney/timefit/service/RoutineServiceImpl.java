package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.*;
import com.callumezmoney.timefit.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class RoutineServiceImpl implements RoutineService {
    private final RoutineRepository routineRepository;
    private final RoutinePlanRepository routinePlanRepository;
    private final WeeklyRoutinePlanRepository weeklyRoutinePlanRepository;
    private final FrequencyRoutinePlanRepository frequencyRoutinePlanRepository;
    private final IndividualRoutinePlanRepository individualRoutinePlanRepository;
    private final UserRepository userRepository;

    public RoutineServiceImpl(RoutineRepository routineRepository,
                              RoutinePlanRepository routinePlanRepository,
                              WeeklyRoutinePlanRepository weeklyRoutinePlanRepository,
                              FrequencyRoutinePlanRepository frequencyRoutinePlanRepository,
                              IndividualRoutinePlanRepository individualRoutinePlanRepository,
                              UserRepository userRepository){
        this.routineRepository = routineRepository;
        this.routinePlanRepository = routinePlanRepository;
        this.weeklyRoutinePlanRepository = weeklyRoutinePlanRepository;
        this.frequencyRoutinePlanRepository = frequencyRoutinePlanRepository;
        this.individualRoutinePlanRepository = individualRoutinePlanRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RoutinePlan> getRoutines() {
        List<RoutinePlan> routines = (List<RoutinePlan>) routinePlanRepository.findAll();
        return routines;
    }

    @Override
    public RoutinePlan getRoutineById(Long id) {
        return null;
    }

    @Override
    public WeeklyRoutinePlan getWeeklyRoutine(Integer weekDay) {
        return null;
    }

    @Override
    public FrequencyRoutinePlan getFrequencyRoutine(Integer index) {
        return null;
    }

    @Override
    public IndividualRoutinePlan getIndividualRoutine(Date date) {
        return null;
    }

    @Override
    public void updateRoutine(RoutinePlan routinePlan) {

    }

    @Override
    public RoutinePlan createRoutine(RoutinePlan routinePlan) {
        return null;
    }

    @Override
    public WeeklyRoutinePlan setWeeklyRoutine(Integer weekDay, Routine routine) {
        return null;
    }

    @Override
    public FrequencyRoutinePlan setFrequencyRoutine(Integer index) {
        return null;
    }

    @Override
    public IndividualRoutinePlan setIndividualRoutine(Date date) {
        return null;
    }

    @Override
    public void deleteRoutine(Long id) {

    }
}
