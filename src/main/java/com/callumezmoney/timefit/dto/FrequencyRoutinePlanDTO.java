package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class FrequencyRoutinePlanDTO{
    Long id;
    String program;
    String routine;
    LocalTime startTime;
    LocalTime endTime;
}
