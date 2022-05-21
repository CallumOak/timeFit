package com.callumezmoney.timefit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class IndividualRoutinePlan{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Routine routine;
    private LocalTime startTime;
    private LocalTime endTime;
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne
    private Program program;

    public void setRoutine(Routine routine) {
        if(routine == this.routine){
            return;
        }
        if(this.routine != null){
            this.routine.getIndividualRoutinePlans().remove(this);
        }
        if(!routine.getIndividualRoutinePlans().contains(this)){
            routine.getIndividualRoutinePlans().add(this);
        }
        this.routine = routine;
    }

    public void setProgram(Program program) {
        if(program == this.program){
            return;
        }
        if(this.program != null){
            this.program.getIndividualRoutines().remove(this);
        }
        if(!program.getIndividualRoutines().contains(this)){
            program.getIndividualRoutines().add(this);
        }
        this.program = program;
    }
}
