package com.callumezmoney.timefit.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class WeeklyRoutinePlanDTO extends RoutinePlanDTO {
    public WeeklyRoutinePlanDTO(Long id, String program, String routine, LocalTime startTime, LocalTime endTime, String type, Integer weekDay, Date date) {
        super(id, program, routine, startTime, endTime, type, weekDay, date);
    }

    public WeeklyRoutinePlanDTO() {
    }

    public WeeklyRoutinePlanDTO(RoutinePlanDTO routinePlanDto) {
        super(routinePlanDto.id, routinePlanDto.program, routinePlanDto.routine, routinePlanDto.startTime, routinePlanDto.endTime, routinePlanDto.type, routinePlanDto.weekDay, routinePlanDto.date);
    }
}
