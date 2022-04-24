package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.ProgramRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final Environment environment;


    @Override
    public Program getProgram(Long Id) {
        return programRepository.findById(Id).get();
    }

    @Override
    public Program getProgram(String programURI) {
        String programIdString = programURI.split(
                environment.getProperty("callumezmoney.app.webapiprefix.program")
            )[0];
        Long programId = Long.parseLong(programIdString);
        return getProgram(programId);
    }
}
