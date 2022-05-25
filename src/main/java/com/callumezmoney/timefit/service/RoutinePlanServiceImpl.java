package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.FrequencyRoutinePlanDTO;
import com.callumezmoney.timefit.dto.IndividualRoutinePlanDTO;
import com.callumezmoney.timefit.dto.WeeklyRoutinePlanDTO;
import com.callumezmoney.timefit.mapper.RoutineMapper;
import com.callumezmoney.timefit.model.FrequencyRoutinePlan;
import com.callumezmoney.timefit.model.IndividualRoutinePlan;
import com.callumezmoney.timefit.model.WeeklyRoutinePlan;
import com.callumezmoney.timefit.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
@AllArgsConstructor
public class RoutinePlanServiceImpl implements RoutinePlanService {
    private final WeeklyRoutinePlanRepository weeklyRoutinePlanRepository;
    private final FrequencyRoutinePlanRepository frequencyRoutinePlanRepository;
    private final IndividualRoutinePlanRepository individualRoutinePlanRepository;

    @Override
    public List<WeeklyRoutinePlan> getWeeklyRoutinePlans() {
        List<WeeklyRoutinePlan> routines = weeklyRoutinePlanRepository.findAll();
        return routines;
    }

    @Override
    public List<FrequencyRoutinePlan> getFrequencyRoutinePlans() {
        List<FrequencyRoutinePlan> routines = frequencyRoutinePlanRepository.findAll();
        return routines;
    }

    @Override
    public List<IndividualRoutinePlan> getIndividualRoutinePlans() {
        List<IndividualRoutinePlan> routines = individualRoutinePlanRepository.findAll();
        return routines;
    }

    @Override
    public Optional<WeeklyRoutinePlan> getWeeklyRoutinePlan(Long id, String username) {
        Optional<WeeklyRoutinePlan> routinePlan = weeklyRoutinePlanRepository.findById(id);
        return routinePlan.isPresent() && validateUser(routinePlan.get(), username) ?
                routinePlan : Optional.empty();
    }

    @Override
    public Optional<FrequencyRoutinePlan> getFrequencyRoutinePlan(Long id, String username) {
        Optional<FrequencyRoutinePlan> routinePlan = frequencyRoutinePlanRepository.findById(id);
        return routinePlan.isPresent() && validateUser(routinePlan.get(), username) ?
                routinePlan : Optional.empty();
    }

    @Override
    public Optional<IndividualRoutinePlan> getIndividualRoutinePlan(Long id, String username) {
        Optional<IndividualRoutinePlan> routinePlan = individualRoutinePlanRepository.findById(id);
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
    @Transactional
    public void editRoutinePlan(WeeklyRoutinePlanDTO routinePlan, String username) {
        Optional<WeeklyRoutinePlan> oldRoutinePlan = weeklyRoutinePlanRepository.findById(routinePlan.getId());
        if(oldRoutinePlan.isPresent() & validateUser(oldRoutinePlan.get(), username)) {
            WeeklyRoutinePlan updatedRoutinePlan = oldRoutinePlan.get();
            updatedRoutinePlan.setStartTime(routinePlan.getStartTime());
            updatedRoutinePlan.setEndTime(routinePlan.getEndTime());
            updatedRoutinePlan.setWeekDay(routinePlan.getWeekDay());
        }
    }

    @Override
    @Transactional
    public void editRoutinePlan(FrequencyRoutinePlanDTO routinePlan, String username) {
        Optional<FrequencyRoutinePlan> oldRoutinePlan = frequencyRoutinePlanRepository.findById(routinePlan.getId());
        if(oldRoutinePlan.isPresent() && validateUser(oldRoutinePlan.get(), username)){
            FrequencyRoutinePlan updatedRoutinePlan = oldRoutinePlan.get();
            updatedRoutinePlan.setStartTime(routinePlan.getStartTime());
            updatedRoutinePlan.setEndTime(routinePlan.getEndTime());

            if(!Objects.equals(updatedRoutinePlan.getPosition(), routinePlan.getPosition())){
                List<FrequencyRoutinePlan> routinePlans = frequencyRoutinePlanRepository.findAll().stream().filter(rp -> Objects.equals(rp.getProgram().getUser().getUsername(), username)).collect(Collectors.toList());

                Integer low = min(routinePlan.getPosition(), updatedRoutinePlan.getPosition());
                Integer high = max(routinePlan.getPosition(), updatedRoutinePlan.getPosition());
                Integer increment = updatedRoutinePlan.getPosition() > routinePlan.getPosition() ? 1 : -1;
                routinePlans.forEach(rp -> {
                    if(rp.getPosition() >= low && rp.getPosition() <= high ){
                        rp.setPosition(rp.getPosition() + increment);
                    }
                });

                updatedRoutinePlan.setPosition(routinePlan.getPosition());
            }
        }
    }

    @Override
    @Transactional
    public void editRoutinePlan(IndividualRoutinePlanDTO routinePlan, String username) {
        Optional<IndividualRoutinePlan> oldRoutinePlan = individualRoutinePlanRepository.findById(routinePlan.getId());
        if(oldRoutinePlan.isPresent() && validateUser(oldRoutinePlan.get(), username)){
            IndividualRoutinePlan updatedRoutinePlan = oldRoutinePlan.get();
            updatedRoutinePlan.setStartTime(routinePlan.getStartTime());
            updatedRoutinePlan.setEndTime(routinePlan.getEndTime());
            updatedRoutinePlan.setDate(routinePlan.getDate());
        }
    }

    @Override
    public Optional<WeeklyRoutinePlan> createRoutinePlan(WeeklyRoutinePlan routinePlan, String username) {
        return Objects.equals(routinePlan.getProgram().getUser().getUsername(), username) ?
                Optional.of(weeklyRoutinePlanRepository.save(routinePlan)) : Optional.empty();
    }

    @Override
    public Optional<FrequencyRoutinePlan> createRoutinePlan(FrequencyRoutinePlan routinePlan, String username) {
        return Objects.equals(routinePlan.getProgram().getUser().getUsername(), username) ?
                Optional.of(frequencyRoutinePlanRepository.save(routinePlan)) : Optional.empty();
    }

    @Override
    public Optional<IndividualRoutinePlan> createRoutinePlan(IndividualRoutinePlan routinePlan, String username) {
        return Objects.equals(routinePlan.getProgram().getUser().getUsername(), username) ?
                Optional.of(individualRoutinePlanRepository.save(routinePlan)) : Optional.empty();
    }

    @Override
    public void deleteRoutinePlan(Long id, String username) {
        Optional<WeeklyRoutinePlan> weeklyRoutinePlan = weeklyRoutinePlanRepository.findById(id);
        Optional<FrequencyRoutinePlan> frequencyRoutinePlan = frequencyRoutinePlanRepository.findById(id);
        Optional<IndividualRoutinePlan> individualRoutinePlan = individualRoutinePlanRepository.findById(id);
        if(weeklyRoutinePlan.isPresent() &&
                validateUser(weeklyRoutinePlan.get(), username)){
            weeklyRoutinePlanRepository.deleteById(id);
        }
        if(frequencyRoutinePlan.isPresent() &&
                validateUser(frequencyRoutinePlan.get(), username)){
            frequencyRoutinePlanRepository.deleteById(id);
        }
        if(individualRoutinePlan.isPresent() &&
                validateUser(individualRoutinePlan.get(), username)){
            individualRoutinePlanRepository.deleteById(id);
        }
    }

    private Boolean validateUser(WeeklyRoutinePlan routinePlan, String username) {
        return Objects.equals(routinePlan.getProgram().getUser().getUsername(), username);
    }

    private Boolean validateUser(FrequencyRoutinePlan routinePlan, String username) {
        return Objects.equals(routinePlan.getProgram().getUser().getUsername(), username);
    }

    private Boolean validateUser(IndividualRoutinePlan routinePlan, String username) {
        return Objects.equals(routinePlan.getProgram().getUser().getUsername(), username);
    }
}
