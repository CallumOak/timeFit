package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.RoutineDTO;
import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.model.ExerciseRoutine;
import com.callumezmoney.timefit.model.Routine;
import com.callumezmoney.timefit.service.RoutineService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
                dto.getColor(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
            );
        routine.setExercises(
                dto.getExercises().stream().map(exerciseMapper::fromURI).collect(Collectors.toList()),
                dto.getExercisePositions()
        );
        return routine;
    }

    @Override
    public RoutineDTO entityToDto(Routine entity) {
        RoutineDTO routine = new RoutineDTO(
                entity.getId(),
                null,
                entity.getName(),
                entity.getColor(),
                entity.getExercises().stream().map(e -> ExerciseMapper.toURI(e.getExercise(), environment))
                        .collect(Collectors.toList()),
                entity.getExercises().stream().map(ExerciseRoutine::getPosition).collect(Collectors.toList()),
                entity.getWeeklyRoutinePlans().stream().map(r -> WeeklyRoutinePlanMapper.toURI(r, environment)).collect(Collectors.toList()),
                entity.getFrequencyRoutinePlans().stream().map(r -> FrequencyRoutinePlanMapper.toURI(r, environment)).collect(Collectors.toList()),
                entity.getIndividualRoutinePlans().stream().map(r -> IndividualRoutinePlanMapper.toURI(r, environment)).collect(Collectors.toList())
        );
        return routine;
    }

    public static  String toURI(Routine object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.routine") + "/" + object.getId();
    }

    @Override
    public Routine fromURI(String uri) {
        return routineService.getRoutine(getIdFromURI(uri, environment, "routine")).get();
    }
}
