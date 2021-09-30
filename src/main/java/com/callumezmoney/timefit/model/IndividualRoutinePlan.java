package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class IndividualRoutinePlan extends  RoutinePlan{

    @Temporal(TemporalType.DATE)
    private Date date;

    public IndividualRoutinePlan() {
    }

}
