package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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
}
