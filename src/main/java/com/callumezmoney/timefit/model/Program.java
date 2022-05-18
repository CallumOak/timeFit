package com.callumezmoney.timefit.model;

import com.callumezmoney.timefit.util.ProgramSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.core.env.Environment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private ProgramSetting programSetting = ProgramSetting.WEEKLY;
    private Integer frequency = 1;
    @OneToMany(mappedBy = "program")
    private List<WeeklyRoutinePlan> weeklyRoutines = new ArrayList<>();
    @OneToMany(mappedBy = "program")
    private List<FrequencyRoutinePlan> frequencyRoutines = new ArrayList<>();
    @OneToMany(mappedBy = "program")
    private List<IndividualRoutinePlan> individualRoutines = new ArrayList<>();
    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", programSetting=" + programSetting +
                ", frequency=" + frequency +
                ", user=" + user.getUsername() +
                '}';
    }
}
