package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.RoutinePlanDTO;
import com.callumezmoney.timefit.model.RoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
public class RoutinePlanMapper implements WebMapper<RoutinePlan, RoutinePlanDTO> {

    private final Environment environment;
    private final RoutineMapper routineMapper;
    private final RoutinePlanService routinePlanService;

    @Override
    public RoutinePlan dtoToEntity(RoutinePlanDTO dto) {
        RoutinePlan routinePlan = new RoutinePlan(
                dto.getId(),
                null,
                routineMapper.fromURI(dto.getRoutine()),
                dto.getStartTime(),
                dto.getEndTime()
        );
        routinePlan.getRoutine().getRoutinePlans().add(routinePlan);
        return routinePlan;
    }

    @Override
    public RoutinePlanDTO entityToDto(RoutinePlan entity) {
        RoutinePlanDTO routinePlan = new RoutinePlanDTO(
                entity.getId(),
                null,
                RoutineMapper.toURI(entity.getRoutine(), environment),
                entity.getStartTime(),
                entity.getEndTime()
        );
        return routinePlan;
    }

    public static  String toURI(RoutinePlan object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.routineplan") + "/" + object.getId();
    }

    @Override
    public RoutinePlan fromURI(String uri) {
        return routinePlanService.getRoutinePlan(getIdFromURI(uri, environment, "routinePlan")).get();
    }
}
