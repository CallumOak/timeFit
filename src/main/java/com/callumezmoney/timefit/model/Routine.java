package com.callumezmoney.timefit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String name;
    private Integer numberOfCycles;
    private Color color;
    @ManyToMany
    private List<Exercise> exercises;
    @OneToMany(mappedBy = "routine", fetch = FetchType.LAZY)
    private List<RoutinePlan> routinePlans;
}
