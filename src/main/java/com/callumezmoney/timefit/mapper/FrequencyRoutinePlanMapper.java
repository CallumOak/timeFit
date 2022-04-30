package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.FrequencyRoutinePlanDTO;
import com.callumezmoney.timefit.model.FrequencyRoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class FrequencyRoutinePlanMapper  implements WebMapper<FrequencyRoutinePlan, FrequencyRoutinePlanDTO>{

    private Environment environment;
    private ProgramMapper programMapper;
    private RoutineMapper routineMapper;
    private RoutinePlanService routinePlanService;

    @Override
    public FrequencyRoutinePlan dtoToEntity(FrequencyRoutinePlanDTO dto) {
        return FrequencyRoutinePlan.builder()
                .id(dto.getId())
                .program(programMapper.dtoToEntity(dto.getProgram()))
                .routine(routineMapper.dtoToEntity(dto.getRoutine()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    @Override
    public FrequencyRoutinePlanDTO entityToDto(FrequencyRoutinePlan entity) {
        return new FrequencyRoutinePlanDTO(
                entity.getId(),
                programMapper.entityToDto(entity.getProgram()),
                routineMapper.entityToDto(entity.getRoutine()),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    @Override
    public String toURI(FrequencyRoutinePlan object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public FrequencyRoutinePlan fromURI(String uri) {
        return routinePlanService.getFrequencyRoutinePlan(getIdFromURI(uri, environment));
    }
}
