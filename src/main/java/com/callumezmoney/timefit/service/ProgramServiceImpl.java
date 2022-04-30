package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.mapper.ProgramMapper;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.User;
import com.callumezmoney.timefit.repository.ProgramRepository;
import com.callumezmoney.timefit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final UserService userService;


    @Override
    public Optional<Program> getProgram(Long id) {
        return programRepository.findById(id);
    }

    @Override
    public Optional<Program> getProgram(Long id, String username) {
        return userService.getUser(username).isPresent() ?
                programRepository.findByIdAndUser(id, userService.getUser(username).get()) : Optional.empty();
    }

    @Override
    public Optional<Program> addProgram(Program program, String username) {
        return Objects.equals(program.getUser().getUsername(), username) ?
            Optional.of(programRepository.save(program)) : Optional.empty();
    }

    @Override
    public void editProgram(Program program, String username) {
        Optional<Program> oldProgram = programRepository.findById(program.getId());
        if(oldProgram.isPresent() && Objects.equals(oldProgram.get().getUser().getUsername(), username)){
            programRepository.save(program);
        }
    }

    @Override
    public void deleteProgram(Long id, String username) {
        if(programRepository.findById(id).isPresent() &&
                Objects.equals(programRepository.findById(id).get().getUser().getUsername(), username)){
            programRepository.deleteById(id);
        }
    }
}
