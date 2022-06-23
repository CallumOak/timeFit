package com.callumezmoney.timefit.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class FrequencyRoutinePlanDTO extends  RoutinePlanDTO{
    public FrequencyRoutinePlanDTO(Long id, String program, String routine, LocalTime startTime, LocalTime endTime, String type, Integer weekDay, Integer position) {
        super(id, program, routine, startTime, endTime, type, weekDay, position);
    }

    public FrequencyRoutinePlanDTO() {
    }

    public FrequencyRoutinePlanDTO(RoutinePlanDTO routinePlanDto) {
        super(routinePlanDto.id, routinePlanDto.program, routinePlanDto.routine, routinePlanDto.startTime, routinePlanDto.endTime, routinePlanDto.type, routinePlanDto.weekDay, routinePlanDto.position);
    }
}
