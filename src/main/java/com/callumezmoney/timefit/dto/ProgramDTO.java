package com.callumezmoney.timefit.dto;
import com.callumezmoney.timefit.util.ProgramSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class ProgramDTO {
    Long id;
    String name;
    ProgramSetting programSetting;
    Integer frequency;
    List<String> weeklyRoutines;
    List<String> frequencyRoutines;
    List<String> individualRoutines;
    String user;
}
