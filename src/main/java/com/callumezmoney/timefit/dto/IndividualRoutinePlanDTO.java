package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class IndividualRoutinePlanDTO {
    Long id;
    String program;
    String routine;
    LocalTime startTime;
    LocalTime endTime;
    Date date;
}
