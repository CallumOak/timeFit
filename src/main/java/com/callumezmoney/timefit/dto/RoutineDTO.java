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
    String color;
    List<String> exercises;
    List<Integer> exercisePositions;
    List<String> weeklyRoutinePlans;
    List<String> frequencyRoutinePlans;
    List<String> individualRoutinePlans;
    String illustrationLocation;
}
