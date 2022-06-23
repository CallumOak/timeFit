package com.callumezmoney.timefit.model;

import com.callumezmoney.timefit.util.ProgramSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    private Date startDate = new Date();
    @OneToMany(
            mappedBy = "program",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WeeklyRoutinePlan> weeklyRoutines = new ArrayList<>();
    @OneToMany(
            mappedBy = "program",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FrequencyRoutinePlan> frequencyRoutines = new ArrayList<>();
    @OneToMany(
            mappedBy = "program",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IndividualRoutinePlan> individualRoutines = new ArrayList<>();
    @ManyToOne
    private User user;

    public void setWeeklyRoutines(List<WeeklyRoutinePlan> weeklyRoutines) {
        if(weeklyRoutines == this.weeklyRoutines){
            return;
        }
        this.weeklyRoutines.forEach(r -> {if(!weeklyRoutines.contains(r))r.setProgram(null);});
        weeklyRoutines.forEach(r -> {if(weeklyRoutines.contains(r))r.setProgram(this);});
        this.weeklyRoutines = weeklyRoutines;
    }

    public void setFrequencyRoutines(List<FrequencyRoutinePlan> frequencyRoutines) {
        if(frequencyRoutines == this.frequencyRoutines){
            return;
        }
        this.frequencyRoutines.forEach(r -> {if(!frequencyRoutines.contains(r))r.setProgram(null);});
        frequencyRoutines.forEach(r -> {if(frequencyRoutines.contains(r))r.setProgram(this);});
        this.frequencyRoutines = frequencyRoutines;
    }

    public void setIndividualRoutines(List<IndividualRoutinePlan> individualRoutines) {
        if(individualRoutines == this.individualRoutines){
            return;
        }
        this.individualRoutines.forEach(r -> {if(!individualRoutines.contains(r))r.setProgram(null);});
        individualRoutines.forEach(r -> {if(individualRoutines.contains(r))r.setProgram(this);});
        this.individualRoutines = individualRoutines;
    }

    public void setUser(User user) {
        if(user == this.user){
            return;
        }
        if(this.user != null){
            this.user.getPrograms().remove(this);
        }
        if(!user.getPrograms().contains(this)){
            user.getPrograms().add(this);
        }
        this.user = user;
    }

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
