package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class WeeklyRoutinePlan extends RoutinePlan {
    private Integer weekDay;
    @ManyToOne
    @JoinColumn(name = "WEEKLY_PROGRAM_ID")
    private Program program;
}
