package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class ExerciseRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer position;

    @ManyToOne
    private Exercise exercise;

    @ManyToOne
    private Routine routine;

    public ExerciseRoutine(Exercise exercise, Routine routine) {
        this.exercise = exercise;
        this.routine = routine;
    }
}
