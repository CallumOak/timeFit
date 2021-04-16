package com.callumezmoney.timefit.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class IndividualRoutinePlan extends  RoutinePlan{
    private Date date;

    public IndividualRoutinePlan() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
