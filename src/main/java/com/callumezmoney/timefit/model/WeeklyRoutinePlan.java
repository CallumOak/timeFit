package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class WeeklyRoutinePlan extends RoutinePlan {
    private Integer weekDay;
}
