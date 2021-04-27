package com.callumezmoney.timefit.model;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class IndividualRoutinePlan extends  RoutinePlan{
    private Date date;

    public IndividualRoutinePlan() {
    }

}
