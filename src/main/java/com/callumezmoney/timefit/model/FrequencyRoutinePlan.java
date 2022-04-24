package com.callumezmoney.timefit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class FrequencyRoutinePlan extends RoutinePlan{
}
