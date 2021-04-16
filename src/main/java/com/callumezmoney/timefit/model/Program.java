package com.callumezmoney.timefit.model;

import com.callumezmoney.timefit.util.ProgramSetting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private ProgramSetting programSetting = ProgramSetting.WEEKLY;
    private Integer frequency = 1;
    @OneToMany
    private List<WeeklyRoutinePlan> weeklyRoutines = new ArrayList<>();
    @OneToMany
    private List<FrequencyRoutinePlan> frequencyRoutines = new ArrayList<>();
    @OneToMany
    private List<IndividualRoutinePlan> individualRoutines = new ArrayList<>();

    public Program() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProgramSetting getProgramSetting() {
        return programSetting;
    }

    public void setProgramSetting(ProgramSetting programSetting) {
        this.programSetting = programSetting;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public List<WeeklyRoutinePlan> getWeeklyRoutines() {
        return weeklyRoutines;
    }

    public void setWeeklyRoutines(List<WeeklyRoutinePlan> weeklyRoutines) {
        this.weeklyRoutines = weeklyRoutines;
    }

    public List<FrequencyRoutinePlan> getFrequencyRoutines() {
        return frequencyRoutines;
    }

    public void setFrequencyRoutines(List<FrequencyRoutinePlan> frequencyRoutines) {
        this.frequencyRoutines = frequencyRoutines;
    }

    public List<IndividualRoutinePlan> getIndividualRoutines() {
        return individualRoutines;
    }

    public void setIndividualRoutines(List<IndividualRoutinePlan> individualRoutines) {
        this.individualRoutines = individualRoutines;
    }
}
