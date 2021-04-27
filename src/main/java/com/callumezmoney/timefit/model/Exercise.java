package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.util.List;
import java.time.Duration;

@Entity
@Data
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Duration exerciseDuration;
    private Duration exerciseBreak;
    private Integer repetitions;
    private String illustrationLocation;
    private Color exerciseColor;
    private Color breakColor;
    private String exerciseSoundLocation;
    private String breakSoundLocation;
    private String countdownSoundLocation;
    @ManyToMany
    private List<Routine> routines;
}
