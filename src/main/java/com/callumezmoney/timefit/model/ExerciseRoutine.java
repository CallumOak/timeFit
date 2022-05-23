package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ExerciseRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Exercise exercise;

    @ManyToOne
    private Routine routine;

    public ExerciseRoutine(Exercise exercise, Routine routine) {
        this.exercise = exercise;
        this.routine = routine;
    }
}
