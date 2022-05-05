package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
public class ExerciseDTO {
    Long id;
    UserDTO user;
    String name;
    Duration exerciseDuration;
    Duration exerciseBreak;
    Integer repetitions;
    Color exerciseColor;
    Color breakColor;
    String illustrationLocation;
    String exerciseSoundLocation;
    String breakSoundLocation;
    String countdownSoundLocation;
    List<RoutineDTO> routines;
}
