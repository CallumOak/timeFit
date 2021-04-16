package com.callumezmoney.timefit.model;

import javax.persistence.*;
import java.awt.*;
import java.util.List;
import java.time.Duration;

@Entity
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

    public Exercise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(Duration exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public Duration getExerciseBreak() {
        return exerciseBreak;
    }

    public void setExerciseBreak(Duration exerciseBreak) {
        this.exerciseBreak = exerciseBreak;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public String getIllustrationLocation() {
        return illustrationLocation;
    }

    public void setIllustrationLocation(String illustrationLocation) {
        this.illustrationLocation = illustrationLocation;
    }

    public Color getExerciseColor() {
        return exerciseColor;
    }

    public void setExerciseColor(Color exerciseColor) {
        this.exerciseColor = exerciseColor;
    }

    public Color getBreakColor() {
        return breakColor;
    }

    public void setBreakColor(Color breakColor) {
        this.breakColor = breakColor;
    }

    public String getExerciseSoundLocation() {
        return exerciseSoundLocation;
    }

    public void setExerciseSoundLocation(String exerciseSoundLocation) {
        this.exerciseSoundLocation = exerciseSoundLocation;
    }

    public String getBreakSoundLocation() {
        return breakSoundLocation;
    }

    public void setBreakSoundLocation(String breakSoundLocation) {
        this.breakSoundLocation = breakSoundLocation;
    }

    public String getCountdownSoundLocation() {
        return countdownSoundLocation;
    }

    public void setCountdownSoundLocation(String countdownSoundLocation) {
        this.countdownSoundLocation = countdownSoundLocation;
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }
}
