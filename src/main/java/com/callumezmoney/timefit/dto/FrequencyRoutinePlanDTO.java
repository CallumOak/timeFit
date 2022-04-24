package com.callumezmoney.timefit.dto;
import lombok.Value;

import java.time.LocalTime;

@Value
public class FrequencyRoutinePlanDTO{
    Long id;
    ProgramDTO program;
    RoutineDTO routine;
    LocalTime startTime;
    LocalTime endTime;
}
