package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    Long id;
    String user;
    String name;
    Duration exerciseDuration;
    Duration exerciseBreak;
    Integer repetitions;
    String exerciseColor;
    String breakColor;
    String illustrationLocation;
    String exerciseSoundLocation;
    String breakSoundLocation;
    String countdownSoundLocation;
    List<String> routines;
    List<Integer> position;
}
