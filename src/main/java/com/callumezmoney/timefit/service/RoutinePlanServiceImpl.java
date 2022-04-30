package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.*;
import com.callumezmoney.timefit.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoutinePlanServiceImpl implements RoutinePlanService {
    private final RoutinePlanRepository routinePlanRepository;
    private final WeeklyRoutinePlanRepository weeklyRoutinePlanRepository;
    private final FrequencyRoutinePlanRepository frequencyRoutinePlanRepository;
    private final IndividualRoutinePlanRepository individualRoutinePlanRepository;
    private final UserService userService;

    @Override
    public List<RoutinePlan> getRoutines() {
        List<RoutinePlan> routines = routinePlanRepository.findAll();
        return routines;
    }

    @Override
    public Optional<RoutinePlan> getRoutinePlan(Long id) {
        return routinePlanRepository.findById(id);
    }

    @Override
    public Optional<RoutinePlan> getRoutinePlan(Long id, String username) {
        Optional<RoutinePlan> routinePlan = routinePlanRepository.findById(id);
        return routinePlan.isPresent() && validateUser(routinePlan.get(), username) ?
                routinePlan : Optional.empty();
    }

    @Override
    public WeeklyRoutinePlan getWeeklyRoutinePlan(Long id) {
        return weeklyRoutinePlanRepository.findById(id).get();
    }

    @Override
    public FrequencyRoutinePlan getFrequencyRoutinePlan(Long id) {
        return frequencyRoutinePlanRepository.findById(id).get();
    }

    @Override
    public IndividualRoutinePlan getIndividualRoutinePlan(Long id) {
        return individualRoutinePlanRepository.findById(id).get();
    }

    @Override
    public void editRoutinePlan(RoutinePlan routinePlan, String username) {
        Optional<RoutinePlan> oldRoutinePlan = routinePlanRepository.findById(routinePlan.getId());
        if(oldRoutinePlan.isPresent() && validateUser(oldRoutinePlan.get(), username)){
            routinePlanRepository.save(routinePlan);
        }
    }

    @Override
    public RoutinePlan createRoutinePlan(RoutinePlan routinePlan, String username) {
        return null;
    }

    @Override
    public WeeklyRoutinePlan editWeeklyRoutinePlan(Integer weekDay, Routine routine) {
        return null;
    }

    @Override
    public FrequencyRoutinePlan editFrequencyRoutinePlan(Integer index) {
        return null;
    }

    @Override
    public IndividualRoutinePlan editIndividualRoutinePlan(Date date) {
        return null;
    }

    @Override
    public void deleteRoutinePlan(Long id, String username) {
        if(routinePlanRepository.findById(id).isPresent() &&
                validateUser(routinePlanRepository.findById(id).get(), username)){
            routinePlanRepository.deleteById(id);
        }
    }

    private Boolean validateUser(RoutinePlan routinePlan, String username) {
        return Objects.equals(routinePlan.getProgram().getUser().getUsername(), username);
    }
}
