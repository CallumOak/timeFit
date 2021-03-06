import {Component, OnDestroy, OnInit} from '@angular/core';
import {RoutineTypeEnum} from "../../enums/routine-type-enum.enum";
import {RoutineService} from "../../service/routine.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {Program} from "../../model/program.model";
import {ProgramService} from "../../service/program.service";
import {Subscription} from "rxjs";
import {RoutinePlanService} from "../../service/routine-plan.service";

const ROUTINE_TYPE = RoutineTypeEnum.individual

@Component({
  selector: 'app-program',
  templateUrl: './program.component.html',
  styleUrls: ['./program.component.css']
})
export class ProgramComponent implements OnInit, OnDestroy {
  program!: Program;
  routineType = ROUTINE_TYPE;
  subscription!: Subscription;

  constructor(private programService: ProgramService,
              public routinePlanService: RoutinePlanService,
              public routineService: RoutineService,
              public router: Router) {
  }

  setSelectedDate(date: Date) {
    this.routinePlanService.selectedDate = date;
  }

  ngOnInit(): void {
    this.subscription = this.programService.program$.subscribe(program => {
      this.program = program;
    })
  }

  updateProgram(event: any){
    //this.program.name = event.target.value;
    let newProgram = {...this.program};
    newProgram.name = event.target.value;
    this.programService.updateProgram(newProgram);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  setWeekly() {
    this.program.programSetting = "weekly";
    this.programService.updateProgram(this.program);
  }

  setFrequency() {
    this.program.programSetting = "frequency";
    this.programService.updateProgram(this.program);
  }
}
