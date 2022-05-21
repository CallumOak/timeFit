package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
public class IndividualRoutinePlanDTO extends RoutinePlanDTO{
    public IndividualRoutinePlanDTO(Long id, String program, String routine, LocalTime startTime, LocalTime endTime, String type, Integer weekDay, Date date) {
        super(id, program, routine, startTime, endTime, type, weekDay, date);
    }

    public IndividualRoutinePlanDTO() {
    }

    public IndividualRoutinePlanDTO(RoutinePlanDTO routinePlanDto) {
        super(routinePlanDto.id, routinePlanDto.program, routinePlanDto.routine, routinePlanDto.startTime, routinePlanDto.endTime, routinePlanDto.type, routinePlanDto.weekDay, routinePlanDto.date);
    }
}
