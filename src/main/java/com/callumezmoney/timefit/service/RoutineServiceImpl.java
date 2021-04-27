package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.RoutineRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class RoutineServiceImpl implements RoutineService {
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

    @Override
    public Set<Routine> getRoutines() {
        Set<Routine> routines = (Set<Routine>) routineRepository.findAll();
        return routines;
    }
}
