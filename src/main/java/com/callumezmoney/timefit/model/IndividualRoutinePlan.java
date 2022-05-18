package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class IndividualRoutinePlan extends  RoutinePlan{
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "INDIVIDUAL_PROGRAM_ID")
    private Program program;
}
