package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ProgramService {
    Program getProgram(Long userId);
}
