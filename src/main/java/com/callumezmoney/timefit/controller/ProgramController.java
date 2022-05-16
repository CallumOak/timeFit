package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.dto.ProgramDTO;
import com.callumezmoney.timefit.mapper.ProgramMapper;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.repository.ProgramRepository;
import com.callumezmoney.timefit.service.ProgramService;
import com.callumezmoney.timefit.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${callumezmoney.app.webapiprefix.program}")
@AllArgsConstructor
@Api(value = "Program API")
public class ProgramController {
    private ProgramService programService;
    private UserService userService;
    private ProgramMapper programMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgram(@PathVariable Long id){
        Optional<Program> program = programService.getProgram(id);
        if(program.isPresent()){
            return ResponseEntity.ok(programMapper.entityToDto(program.get()));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Program not found"));
    }

    @GetMapping()
    public ResponseEntity<?> listProgram(Principal principal){
        return ResponseEntity.ok(
                programService.getPrograms(principal.getName())
                .stream().map(programMapper::entityToDto).collect(Collectors.toList())
        );
    }

    @PostMapping()
    public ResponseEntity<?> addProgram(@RequestBody ProgramDTO newProgramDto, Principal principal){
        Program newProgram = programMapper.dtoToEntity(newProgramDto);
        newProgram.setUser(userService.getUser(principal.getName()).orElse(null));
        return ResponseEntity.ok(programMapper.entityToDto(programService.addProgram(newProgram, principal.getName()).get()));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editProgram(@RequestBody ProgramDTO newProgramDto, Principal principal){
        Program newProgram = programMapper.dtoToEntity(newProgramDto);
        programService.editProgram(newProgram, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteProgram(@PathVariable Long id, Principal principal){
        programService.deleteProgram(id, principal.getName());
    }
}
