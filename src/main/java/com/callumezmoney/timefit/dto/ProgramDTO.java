package com.callumezmoney.timefit.dto;
import com.callumezmoney.timefit.util.ProgramSetting;
import lombok.Value;

import java.util.List;

@Value
public class ProgramDTO {
    Long id;
    String name;
    ProgramSetting programSetting;
    Integer frequency;
    List<WeeklyRoutinePlanDTO> weeklyRoutines;
    List<FrequencyRoutinePlanDTO> frequencyRoutines;
    List<IndividualRoutinePlanDTO> individualRoutines;
}
