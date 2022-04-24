package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.RoutinePlanDTO;
import com.callumezmoney.timefit.model.RoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class RoutinePlanMapper implements WebMapper<RoutinePlan, RoutinePlanDTO> {

    private Environment environment;
    private final ProgramMapper programMapper;
    private final RoutineMapper routineMapper;
    private RoutinePlanService routinePlanService;

    @Override
    public RoutinePlan dtoToEntity(RoutinePlanDTO dto) {
        RoutinePlan routinePlan = new RoutinePlan(
                dto.getId(),
                programMapper.dtoToEntity(dto.getProgram()),
                routineMapper.dtoToEntity(dto.getRoutine()),
                dto.getStartTime(),
                dto.getEndTime()
        );
        return routinePlan;
    }

    @Override
    public RoutinePlanDTO entityToDto(RoutinePlan entity) {
        RoutinePlanDTO routinePlan = new RoutinePlanDTO(
                entity.getId(),
                programMapper.entityToDto(entity.getProgram()),
                routineMapper.entityToDto(entity.getRoutine()),
                entity.getStartTime(),
                entity.getEndTime()
        );
        return routinePlan;
    }

    @Override
    public String toURI(RoutinePlan object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public RoutinePlan fromURI(String uri) {
        return routinePlanService.getRoutinePlanById(getIdFromURI(uri, environment));
    }
}
