package com.callumezmoney.timefit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Entity
public class RoutinePlan {
    @Id
    private Long id;
    @ManyToOne
    private Program programs;
    @ManyToOne
    private Routine routine;
    private LocalTime startTime;
    private LocalTime endTime;

    public RoutinePlan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Program getPrograms() {
        return programs;
    }

    public void setPrograms(Program programs) {
        this.programs = programs;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
