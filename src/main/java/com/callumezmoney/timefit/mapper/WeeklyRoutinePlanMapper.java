package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.WeeklyRoutinePlanDTO;
import com.callumezmoney.timefit.model.WeeklyRoutinePlan;
import com.callumezmoney.timefit.service.RoutinePlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
public class WeeklyRoutinePlanMapper implements WebMapper<WeeklyRoutinePlan, WeeklyRoutinePlanDTO>{

    private Environment environment;
    private RoutineMapper routineMapper;
    private RoutinePlanService routinePlanService;

    @Override
    public WeeklyRoutinePlan dtoToEntity(WeeklyRoutinePlanDTO dto) {
        return WeeklyRoutinePlan.builder()
                .weekDay(dto.getWeekDay())
                .id(dto.getId())
                .program(null)
                .routine(routineMapper.fromURI(dto.getRoutine()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    @Override
    public WeeklyRoutinePlanDTO entityToDto(WeeklyRoutinePlan entity) {
        return new WeeklyRoutinePlanDTO(
                entity.getId(),
                ProgramMapper.toURI(entity.getProgram(), environment),
                RoutineMapper.toURI(entity.getRoutine(), environment),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getWeekDay()
        );
    }

    public static String toURI(WeeklyRoutinePlan object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.routineplan") + "/" + object.getId();
    }

    @Override
    public WeeklyRoutinePlan fromURI(String uri) {
        return routinePlanService.getWeeklyRoutinePlan(getIdFromURI(uri, environment));
    }
}