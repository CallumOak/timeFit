package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.repository.ProgramRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/program/")
@AllArgsConstructor
public class ProgramController {
    ProgramRepository programRepository;

    @GetMapping("{id}")
    public ResponseEntity<?> getProgram(@PathVariable Long id){
        Optional<Program> program = programRepository.findById(id);
        if(program.isPresent()){
            return ResponseEntity.ok(program.get());
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Program not found"));
    }

    @GetMapping()
    public ResponseEntity<?> listProgram(){
        return ResponseEntity.ok(programRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> addProgram(@RequestBody Program newProgram){
        return ResponseEntity.ok(programRepository.save(newProgram));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editProgram(@RequestBody Program newProgram){
        Optional<Program> program = programRepository.findById(newProgram.getId());
        if(program.isPresent()){
            programRepository.save(newProgram);
        }
        else{
            throw new NullPointerException();
        }
    }

    @DeleteMapping("{id}")
    public void deleteProgram(@PathVariable Long id){
        programRepository.deleteById(id);
    }
}
