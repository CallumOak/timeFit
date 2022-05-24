package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class FrequencyRoutinePlan{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Routine routine;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    private Program program;
    private Integer position;

    public void setRoutine(Routine routine) {
        if(routine == this.routine){
            return;
        }
        if(this.routine != null){
            this.routine.getFrequencyRoutinePlans().remove(this);
        }
        if(!routine.getFrequencyRoutinePlans().contains(this)){
            routine.getFrequencyRoutinePlans().add(this);
        }
        this.routine = routine;
    }

    public void setProgram(Program program) {
        if(program == this.program){
            return;
        }
        if(this.program != null){
            this.program.getFrequencyRoutines().remove(this);
        }
        if(!program.getFrequencyRoutines().contains(this)){
            program.getFrequencyRoutines().add(this);
        }
        this.program = program;
    }
}
