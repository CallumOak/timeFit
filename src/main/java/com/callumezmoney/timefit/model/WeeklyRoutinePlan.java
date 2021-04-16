package com.callumezmoney.timefit.model;

import javax.persistence.Entity;

@Entity
public class WeeklyRoutinePlan extends RoutinePlan {
    private Integer weekDay;

    public WeeklyRoutinePlan() {
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }
}
