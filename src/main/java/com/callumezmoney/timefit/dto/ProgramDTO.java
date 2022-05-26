package com.callumezmoney.timefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDTO {
    Long id;
    String name;
    String programSetting;
    Integer frequency;
    String startDate;
    List<String> weeklyRoutinePlans;
    List<String> frequencyRoutinePlans;
    List<String> individualRoutinePlans;
    String user;
}
