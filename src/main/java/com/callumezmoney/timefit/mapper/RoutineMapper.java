package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.RoutineDTO;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.service.RoutineService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class RoutineMapper implements WebMapper<Routine, RoutineDTO> {

    private Environment environment;
    private final ExerciseMapper exerciseMapper;
    private final RoutinePlanMapper routinePlanMapper;
    private final UserMapper userMapper;
    private RoutineService routineService;

    @Override
    public Routine dtoToEntity(RoutineDTO dto) {
        Routine routine = new Routine(
                dto.getId(),
                userMapper.dtoToEntity(dto.getUser()),
                dto.getName(),
                dto.getNumberOfCycles(),
                dto.getColor(),
                dto.getExercises().stream().map(exerciseMapper::dtoToEntity)
                        .collect(Collectors.toList()),
                dto.getRoutinePlans().stream().map(routinePlanMapper::dtoToEntity)
                        .collect(Collectors.toList())
            );
        return routine;
    }

    @Override
    public RoutineDTO entityToDto(Routine entity) {
        RoutineDTO routine = new RoutineDTO(
                entity.getId(),
                userMapper.entityToDto(entity.getUser()),
                entity.getName(),
                entity.getNumberOfCycles(),
                entity.getColor(),
                entity.getExercises().stream().map(exerciseMapper::entityToDto)
                        .collect(Collectors.toList()),
                entity.getRoutinePlans().stream().map(routinePlanMapper::entityToDto)
                        .collect(Collectors.toList())
        );
        return routine;
    }

    @Override
    public String toURI(Routine object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public Routine fromURI(String uri) {
        return routineService.getRoutine(getIdFromURI(uri, environment));
    }
}
