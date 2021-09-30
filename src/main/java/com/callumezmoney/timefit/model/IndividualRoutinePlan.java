package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class IndividualRoutinePlan extends  RoutinePlan{
    @Temporal(TemporalType.DATE)
    private Date date;
}
