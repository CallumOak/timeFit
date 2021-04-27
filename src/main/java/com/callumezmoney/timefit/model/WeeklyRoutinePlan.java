package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class WeeklyRoutinePlan extends RoutinePlan {
    private Integer weekDay;

    public WeeklyRoutinePlan() {
    }
}
