package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class FrequencyRoutinePlan extends RoutinePlan{
    @ManyToOne
    @JoinColumn(name = "FREQUENCY_PROGRAM_ID")
    private Program program;
}
