package com.callumezmoney.timefit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoutinePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Transient
    @ToString.Exclude
    private Program program;
    @ManyToOne
    private Routine routine;
    private LocalTime startTime;
    private LocalTime endTime;
}
