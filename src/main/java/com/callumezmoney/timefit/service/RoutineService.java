package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoutineService {
    Set<Routine> getRoutines();
}
