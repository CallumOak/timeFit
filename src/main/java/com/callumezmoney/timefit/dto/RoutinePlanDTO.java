package com.callumezmoney.timefit.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutinePlanDTO {
    Long id;
    String program;
    String routine;
    LocalTime startTime;
    LocalTime endTime;
}
