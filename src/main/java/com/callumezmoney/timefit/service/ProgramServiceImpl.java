package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.ProgramRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final UserRepository userRepository;


    @Override
    public Program getProgram(Long userId) {
        User user = userRepository.findById(userId).get();
        Program program = user.getProgram();
        return program;
    }
}
