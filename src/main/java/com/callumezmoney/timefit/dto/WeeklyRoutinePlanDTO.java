package com.callumezmoney.timefit.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class WeeklyRoutinePlanDTO {
    Long id;
    String program;
    String routine;
    LocalTime startTime;
    LocalTime endTime;
    Integer weekDay;
}
