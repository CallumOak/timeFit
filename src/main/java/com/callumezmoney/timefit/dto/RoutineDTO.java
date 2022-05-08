package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RoutineDTO {
    Long id;
    String user;
    String name;
    Integer numberOfCycles;
    Color color;
    List<String> exercises;
    List<String> routinePlans;
}
