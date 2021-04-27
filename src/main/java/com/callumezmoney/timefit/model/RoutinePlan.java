package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Entity
@Data
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
