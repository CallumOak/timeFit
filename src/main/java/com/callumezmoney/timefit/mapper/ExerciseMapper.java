package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.ExerciseDTO;
import com.callumezmoney.timefit.model.Exercise;
import com.callumezmoney.timefit.service.ExerciseService;
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
public class ExerciseMapper implements WebMapper<Exercise, ExerciseDTO> {

    private Environment environment;
    private ExerciseService exerciseService;

    @Override
    public Exercise dtoToEntity(ExerciseDTO dto) {
        return new Exercise(
                dto.getId(),
                null,
                dto.getName(),
                dto.getExerciseDuration(),
                dto.getExerciseBreak(),
                dto.getRepetitions(),
                dto.getExerciseColor(),
                dto.getBreakColor(),
                dto.getIllustrationLocation(),
                dto.getExerciseSoundLocation(),
                dto.getBreakSoundLocation(),
                dto.getCountdownSoundLocation(),
                new ArrayList<>()
        );
    }

    @Override
    public ExerciseDTO entityToDto(Exercise entity) {
        return new ExerciseDTO(
                entity.getId(),
                UserMapper.toURI(entity.getUser(), environment),
                entity.getName(),
                entity.getExerciseDuration(),
                entity.getExerciseBreak(),
                entity.getRepetitions(),
                entity.getExerciseColor(),
                entity.getBreakColor(),
                entity.getIllustrationLocation(),
                entity.getExerciseSoundLocation(),
                entity.getBreakSoundLocation(),
                entity.getCountdownSoundLocation(),
                entity.getRoutines().stream().map(r -> RoutineMapper.toURI(r, environment)).collect(Collectors.toList())
        );
    }

    public static String toURI(Exercise object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.exercise") + "/" + object.getId();
    }

    @Override
    public Exercise fromURI(String uri) {
        return exerciseService.getExercise(getIdFromURI(uri, environment, "exercise")).get();
    }
}
