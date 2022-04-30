package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ProgramService {
    Optional<Program> getProgram(Long id);
    Optional<Program> getProgram(Long id, String username);
    Optional<Program> addProgram(Program program, String username);
    void editProgram(Program program, String username);
    void deleteProgram(Long id, String username);
}
