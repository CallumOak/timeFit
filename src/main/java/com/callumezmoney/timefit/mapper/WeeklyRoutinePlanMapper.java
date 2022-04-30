package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.WeeklyRoutinePlanDTO;
import com.callumezmoney.timefit.model.WeeklyRoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class WeeklyRoutinePlanMapper implements WebMapper<WeeklyRoutinePlan, WeeklyRoutinePlanDTO>{

    private Environment environment;
    private ProgramMapper programMapper;
    private RoutineMapper routineMapper;
    private RoutinePlanService routinePlanService;

    @Override
    public WeeklyRoutinePlan dtoToEntity(WeeklyRoutinePlanDTO dto) {
        return WeeklyRoutinePlan.builder()
                .weekDay(dto.getWeekDay())
                .id(dto.getId())
                .program(programMapper.dtoToEntity(dto.getProgram()))
                .routine(routineMapper.dtoToEntity(dto.getRoutine()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    @Override
    public WeeklyRoutinePlanDTO entityToDto(WeeklyRoutinePlan entity) {
        return new WeeklyRoutinePlanDTO(
                entity.getId(),
                programMapper.entityToDto(entity.getProgram()),
                routineMapper.entityToDto(entity.getRoutine()),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getWeekDay()
        );
    }

    @Override
    public String toURI(WeeklyRoutinePlan object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public WeeklyRoutinePlan fromURI(String uri) {
        return routinePlanService.getWeeklyRoutinePlan(getIdFromURI(uri, environment));
    }
}
