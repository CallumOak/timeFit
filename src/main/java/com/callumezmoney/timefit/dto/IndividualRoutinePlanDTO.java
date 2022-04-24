package com.callumezmoney.timefit.dto;
import lombok.Value;

import java.time.LocalTime;
import java.util.Date;

@Value
public class IndividualRoutinePlanDTO {
    Long id;
    ProgramDTO program;
    RoutineDTO routine;
    LocalTime startTime;
    LocalTime endTime;
    Date date;
}
