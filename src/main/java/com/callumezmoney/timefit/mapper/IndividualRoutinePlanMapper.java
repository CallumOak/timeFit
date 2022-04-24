package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.IndividualRoutinePlanDTO;
import com.callumezmoney.timefit.model.IndividualRoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class IndividualRoutinePlanMapper  implements WebMapper<IndividualRoutinePlan, IndividualRoutinePlanDTO>{

    private Environment environment;
    private ProgramMapper programMapper;
    private RoutineMapper routineMapper;
    private RoutinePlanService routinePlanService;

    @Override
    public IndividualRoutinePlan dtoToEntity(IndividualRoutinePlanDTO dto) {
        return IndividualRoutinePlan.builder()
                .date(dto.getDate())
                .id(dto.getId())
                .program(programMapper.dtoToEntity(dto.getProgram()))
                .routine(routineMapper.dtoToEntity(dto.getRoutine()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    @Override
    public IndividualRoutinePlanDTO entityToDto(IndividualRoutinePlan entity) {
        return new IndividualRoutinePlanDTO(
                entity.getId(),
                programMapper.entityToDto(entity.getProgram()),
                routineMapper.entityToDto(entity.getRoutine()),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getDate()
        );
    }

    @Override
    public String toURI(IndividualRoutinePlan object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public IndividualRoutinePlan fromURI(String uri) {
        return routinePlanService.getIndividualRoutine(getIdFromURI(uri, environment));
    }
}
