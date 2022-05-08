package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.FrequencyRoutinePlanDTO;
import com.callumezmoney.timefit.model.FrequencyRoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
public class FrequencyRoutinePlanMapper  implements WebMapper<FrequencyRoutinePlan, FrequencyRoutinePlanDTO>{

    private Environment environment;
    private RoutineMapper routineMapper;
    private RoutinePlanService routinePlanService;

    @Override
    public FrequencyRoutinePlan dtoToEntity(FrequencyRoutinePlanDTO dto) {
        return FrequencyRoutinePlan.builder()
                .id(dto.getId())
                .program(null)
                .routine(routineMapper.fromURI(dto.getRoutine()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    @Override
    public FrequencyRoutinePlanDTO entityToDto(FrequencyRoutinePlan entity) {
        return new FrequencyRoutinePlanDTO(
                entity.getId(),
                ProgramMapper.toURI(entity.getProgram(), environment),
                RoutineMapper.toURI(entity.getRoutine(), environment),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    public static String toURI(FrequencyRoutinePlan object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.routineplan") + "/" + object.getId();
    }

    @Override
    public FrequencyRoutinePlan fromURI(String uri) {
        return routinePlanService.getFrequencyRoutinePlan(getIdFromURI(uri, environment));
    }
}