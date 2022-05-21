package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.dto.FrequencyRoutinePlanDTO;
import com.callumezmoney.timefit.dto.IndividualRoutinePlanDTO;
import com.callumezmoney.timefit.dto.RoutinePlanDTO;
import com.callumezmoney.timefit.dto.WeeklyRoutinePlanDTO;
import com.callumezmoney.timefit.mapper.FrequencyRoutinePlanMapper;
import com.callumezmoney.timefit.mapper.IndividualRoutinePlanMapper;
import com.callumezmoney.timefit.mapper.WeeklyRoutinePlanMapper;
import com.callumezmoney.timefit.model.FrequencyRoutinePlan;
import com.callumezmoney.timefit.model.IndividualRoutinePlan;
import com.callumezmoney.timefit.model.WeeklyRoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import com.callumezmoney.timefit.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("${callumezmoney.app.webapiprefix.routineplan}")
@AllArgsConstructor
@Api(value = "RoutinePlan API")
public class RoutinePlanController {

    private final RoutinePlanService routinePlanService;
    private final UserService userService;
    private final WeeklyRoutinePlanMapper weeklyRoutinePlanMapper;
    private final FrequencyRoutinePlanMapper frequencyRoutinePlanMapper;
    private final IndividualRoutinePlanMapper individualRoutinePlanMapper;

    @GetMapping("/weekly/{id}")
    public ResponseEntity<?> getWeeklyRoutinePlanById(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(weeklyRoutinePlanMapper.entityToDto(routinePlanService.getWeeklyRoutinePlan(id, principal.getName()).get()));
    }
    @GetMapping("/frequency/{id}")
    public ResponseEntity<?> getFrequencyRoutinePlanById(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(frequencyRoutinePlanMapper.entityToDto(routinePlanService.getFrequencyRoutinePlan(id, principal.getName()).get()));
    }
    @GetMapping("/individual/{id}")
    public ResponseEntity<?> getIndividualRoutinePlanById(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(individualRoutinePlanMapper.entityToDto(routinePlanService.getIndividualRoutinePlan(id, principal.getName()).get()));
    }

    @GetMapping("/weekly")
    public ResponseEntity<?> listWeeklyRoutinePlans(Principal principal){
        List<RoutinePlanDTO> routinePlanDtos = routinePlanService.getWeeklyRoutinePlans()
                        .stream().map(weeklyRoutinePlanMapper::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(routinePlanDtos);
    }
    @GetMapping("/frequency")
    public ResponseEntity<?> listFrequencyRoutinePlans(Principal principal){
        List<RoutinePlanDTO> routinePlanDtos = routinePlanService.getFrequencyRoutinePlans()
                .stream().map(frequencyRoutinePlanMapper::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(routinePlanDtos);
    }
    @GetMapping("/individual")
    public ResponseEntity<?> listIndividualRoutinePlans(Principal principal){
        List<RoutinePlanDTO> routinePlanDtos = routinePlanService.getIndividualRoutinePlans()
                .stream().map(individualRoutinePlanMapper::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(routinePlanDtos);
    }

    @PostMapping ("")
    public ResponseEntity<?> addRoutinePlan(@RequestBody RoutinePlanDTO routinePlanDto, Principal principal){
        switch(routinePlanDto.getType()){
            case "weekly":
                return addWeeklyRoutinePlan(new WeeklyRoutinePlanDTO(routinePlanDto), principal);
            case "frequency":
                return addFrequencyRoutinePlan(new FrequencyRoutinePlanDTO(routinePlanDto), principal);
            case "individual":
                return addIndividualRoutinePlan(new IndividualRoutinePlanDTO(routinePlanDto), principal);
            default:
                return ResponseEntity.badRequest().body("Type of routine plan not recognised : " + routinePlanDto.getType());
        }
    }

    private ResponseEntity<?> addWeeklyRoutinePlan(WeeklyRoutinePlanDTO routinePlanDto, Principal principal) {
        WeeklyRoutinePlan routinePlan = weeklyRoutinePlanMapper.dtoToEntity(routinePlanDto);
        routinePlan.setProgram(userService.getUser(principal.getName()).orElse(null).getPrograms().get(0));
        return ResponseEntity.ok(weeklyRoutinePlanMapper.entityToDto(routinePlanService.createRoutinePlan(routinePlan, principal.getName()).get()));
    }

    private ResponseEntity<?> addFrequencyRoutinePlan(FrequencyRoutinePlanDTO routinePlanDto, Principal principal) {
        FrequencyRoutinePlan routinePlan = frequencyRoutinePlanMapper.dtoToEntity(routinePlanDto);
        routinePlan.setProgram(userService.getUser(principal.getName()).orElse(null).getPrograms().get(0));
        return ResponseEntity.ok(frequencyRoutinePlanMapper.entityToDto(routinePlanService.createRoutinePlan(routinePlan, principal.getName()).get()));
    }

    private ResponseEntity<?> addIndividualRoutinePlan(IndividualRoutinePlanDTO routinePlanDto, Principal principal) {
        IndividualRoutinePlan routinePlan = individualRoutinePlanMapper.dtoToEntity(routinePlanDto);
        routinePlan.setProgram(userService.getUser(principal.getName()).orElse(null).getPrograms().get(0));
        return ResponseEntity.ok(individualRoutinePlanMapper.entityToDto(routinePlanService.createRoutinePlan(routinePlan, principal.getName()).get()));
    }


    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> editRoutinePlan(@RequestBody RoutinePlanDTO routinePlanDto, Principal principal){
        switch(routinePlanDto.getType()){
            case "weekly":
                routinePlanService.editRoutinePlan(new WeeklyRoutinePlanDTO(routinePlanDto), principal.getName());
                return ResponseEntity.noContent().build();
            case "frequency":
                routinePlanService.editRoutinePlan(new FrequencyRoutinePlanDTO(routinePlanDto), principal.getName());
                return ResponseEntity.noContent().build();
            case "individual":
                routinePlanService.editRoutinePlan(new IndividualRoutinePlanDTO(routinePlanDto), principal.getName());
                return ResponseEntity.noContent().build();
            default:
                return ResponseEntity.badRequest().body("Type of routine plan not recognised : " + routinePlanDto.getType());
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoutinePlan(@PathVariable Long id, Principal principal){
        routinePlanService.deleteRoutinePlan(id, principal.getName());
    }


}
