package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrequencyRoutinePlanDTO{
    Long id;
    String program;
    String routine;
    LocalTime startTime;
    LocalTime endTime;
}
