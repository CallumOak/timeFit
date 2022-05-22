package com.callumezmoney.timefit.dto;
import com.callumezmoney.timefit.util.ProgramSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDTO {
    Long id;
    String name;
    ProgramSetting programSetting;
    Integer frequency;
    List<String> weeklyRoutinePlans;
    List<String> frequencyRoutinePlans;
    List<String> individualRoutinePlans;
    String user;
}
