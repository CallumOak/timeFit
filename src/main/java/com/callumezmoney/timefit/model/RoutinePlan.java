package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RoutinePlan {
    @Id
    private Long id;
    @ManyToOne
    private Program program;
    @ManyToOne
    private Routine routine;
    private LocalTime startTime;
    private LocalTime endTime;
}
