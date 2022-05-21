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
public class WeeklyRoutinePlan{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Routine routine;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer weekDay;
    @ManyToOne(fetch = FetchType.LAZY)
    private Program program;

    public void setRoutine(Routine routine) {
        if(routine == this.routine){
            return;
        }
        if(this.routine != null){
            this.routine.getWeeklyRoutinePlans().remove(this);
        }
        if(!routine.getWeeklyRoutinePlans().contains(this)){
            routine.getWeeklyRoutinePlans().add(this);
        }
        this.routine = routine;
    }

    public void setProgram(Program program) {
        if(program == this.program){
            return;
        }
        if(this.program != null){
            this.program.getWeeklyRoutines().remove(this);
        }
        if(!program.getWeeklyRoutines().contains(this)){
            program.getWeeklyRoutines().add(this);
        }
        this.program = program;
    }
}
