package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.IndividualRoutinePlanDTO;
import com.callumezmoney.timefit.dto.RoutinePlanDTO;
import com.callumezmoney.timefit.model.IndividualRoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
public class IndividualRoutinePlanMapper  implements WebMapper<IndividualRoutinePlan, IndividualRoutinePlanDTO>{

    private Environment environment;
    private RoutineMapper routineMapper;
    private RoutinePlanService routinePlanService;

    @Override
    public IndividualRoutinePlan dtoToEntity(IndividualRoutinePlanDTO dto) {
        IndividualRoutinePlan routinePlan = IndividualRoutinePlan.builder()
                .id(dto.getId())
                .program(null)
                .routine(routineMapper.fromURI(dto.getRoutine()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .date(dto.getDate())
                .build();
        routinePlan.getRoutine().getIndividualRoutinePlans().add(routinePlan);
        return routinePlan;
    }

    @Override
    public IndividualRoutinePlanDTO entityToDto(IndividualRoutinePlan entity) {
        IndividualRoutinePlanDTO routinePlan = new IndividualRoutinePlanDTO(
                entity.getId(),
                ProgramMapper.toURI(entity.getProgram(), environment),
                RoutineMapper.toURI(entity.getRoutine(), environment),
                entity.getStartTime(),
                entity.getEndTime(),
                "individual",
                null,
                entity.getDate()
        );
        return routinePlan;
    }

    public static String toURI(IndividualRoutinePlan object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.routineplan") + "/" + object.getId();
    }

    @Override
    public IndividualRoutinePlan fromURI(String uri) {
        return routinePlanService.getIndividualRoutinePlan(getIdFromURI(uri, environment, "routineplan"));
    }
}
