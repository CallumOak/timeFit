package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.dto.RoutineDTO;
import com.callumezmoney.timefit.mapper.RoutineMapper;
import com.callumezmoney.timefit.model.Program;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import com.callumezmoney.timefit.service.RoutineService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${callumezmoney.app.webapiprefix.routine}")
@AllArgsConstructor
@Api(value = "Routine API")
public class RoutineController {
    private RoutineService routineService;
    private RoutineMapper routineMapper;

    @GetMapping
    public ResponseEntity<?> getRoutines(Principal principal) {
        return ResponseEntity.ok(
                routineService.getRoutines(principal.getName())
                .stream().map(routineMapper::entityToDto).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoutine(@PathVariable Long id, Principal principal) {
        Optional<Routine> routine = routineService.getRoutine(id, principal.getName());
        return routine.isPresent() ?
            ResponseEntity.ok(routineMapper.entityToDto(routine.get())) :
            ResponseEntity.badRequest().body(new MessageResponse("Error: Routine not found"));
    }

    @PostMapping
    public ResponseEntity<?> addRoutine(@RequestBody RoutineDTO routineDto, Principal principal){
        Routine routine = routineMapper.dtoToEntity(routineDto);
        return ResponseEntity.ok(routineMapper.entityToDto(routineService.createRoutine(routine, principal.getName()).get()));
    }

    @PutMapping
    public void editRoutine(@RequestBody RoutineDTO routineDto, Principal principal){
        Routine routine = routineMapper.dtoToEntity(routineDto);
        routineService.updateRoutine(routine, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteRoutine(@PathVariable Long id, Principal principal){
        routineService.deleteRoutine(id, principal.getName());
    }
}
