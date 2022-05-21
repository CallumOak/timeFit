package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDTO {
    Long id;
    String user;
    String name;
    Integer numberOfCycles;
    Color color;
    List<String> exercises;
    List<String> weeklyRoutinePlans;
    List<String> FrequencyRoutinePlans;
    List<String> IndividualRoutinePlans;
}
