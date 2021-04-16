package com.callumezmoney.timefit.model;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Entity
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer numberOfCycles;
    private Color color;
    @ManyToMany
    private List<Exercise> exercises = new ArrayList<>();
    @OneToMany
    private List<RoutinePlan> routinePlans = new ArrayList<>();

    public Routine() {
    }

    public Long duration(RoutinePlan routinePlan){
        return MINUTES.between(routinePlan.getStartTime(), routinePlan.getEndTime());
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

    public Integer getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(Integer numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<RoutinePlan> getRoutinePlans() {
        return routinePlans;
    }

    public void setRoutinePlans(List<RoutinePlan> routinePlans) {
        this.routinePlans = routinePlans;
    }
}
