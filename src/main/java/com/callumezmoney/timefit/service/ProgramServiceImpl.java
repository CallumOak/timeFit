package com.callumezmoney.timefit.service;

import com.callumezmoney.timefit.dto.ProgramDTO;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.repository.ProgramRepository;
import com.callumezmoney.timefit.util.ProgramSetting;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.ProgramSetting.fromValue;

@Service
@AllArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final UserService userService;


    @Override
    public List<Program> getPrograms(String username) {
        return programRepository.findAll().stream().filter(p -> p.getUser().getUsername().equals(username)).collect(Collectors.toList());
    }

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
    @Transactional
    public void editProgram(ProgramDTO program, String username) {
        Optional<Program> oldProgram = programRepository.findById(program.getId());
        if(oldProgram.isPresent() && Objects.equals(oldProgram.get().getUser().getUsername(), username)){
            Program updatedProgram = oldProgram.get();
            updatedProgram.setName(program.getName());
            updatedProgram.setProgramSetting(fromValue(program.getProgramSetting()));
            try {
                updatedProgram.setStartDate((new SimpleDateFormat("yyyy-MM-dd")).parse(program.getStartDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            updatedProgram.setFrequency(program.getFrequency());
        }
    }

    @Override
    public void deleteProgram(Long id, String username) {
        Optional<Program> program = programRepository.findById(id);
        if(program.isPresent() &&
                Objects.equals(program.get().getUser().getUsername(), username)){
            programRepository.deleteById(id);
        }
    }
}
