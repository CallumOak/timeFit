package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.RoutineDTO;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.service.RoutineService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
public class RoutineMapper implements WebMapper<Routine, RoutineDTO> {

    private Environment environment;
    private final ExerciseMapper exerciseMapper;
    private RoutineService routineService;

    @Override
    public Routine dtoToEntity(RoutineDTO dto) {
        Routine routine = new Routine(
                dto.getId(),
                null,
                dto.getName(),
                dto.getNumberOfCycles(),
                dto.getColor(),
                dto.getExercises().stream().map(exerciseMapper::fromURI)
                        .collect(Collectors.toList()),
                new ArrayList()
            );
        routine.getExercises().forEach(e -> e.getRoutines().add(routine));
        return routine;
    }

    @Override
    public RoutineDTO entityToDto(Routine entity) {
        RoutineDTO routine = new RoutineDTO(
                entity.getId(),
                null,
                entity.getName(),
                entity.getNumberOfCycles(),
                entity.getColor(),
                entity.getExercises().stream().map(e -> ExerciseMapper.toURI(e, environment))
                        .collect(Collectors.toList()),
                entity.getRoutinePlans().stream().map(r -> RoutinePlanMapper.toURI(r, environment)).collect(Collectors.toList())
        );
        return routine;
    }

    public static  String toURI(Routine object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.routine") + "/" + object.getId();
    }

    @Override
    public Routine fromURI(String uri) {
        return routineService.getRoutine(getIdFromURI(uri, environment)).get();
    }
}
