import { Component, OnInit } from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {Subscription} from "rxjs";
import {NavbarService} from "../../../service/navbar.service";
import {Routine} from "../../../model/routine.model";
import {Program} from "../../../model/program.model";
import {ProgramService} from "../../../service/program.service";
import {RoutinePlan} from "../../../model/routine-plan.model";
import {RoutinePlanService} from "../../../service/routine-plan.service";

const ROUTINE_TYPE = RoutineTypeEnum.weekly
const WEEK_DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

@Component({
  selector: 'app-weekly-program',
  templateUrl: './weekly-program.component.html',
  styleUrls: ['./weekly-program.component.css']
})
export class WeeklyProgramComponent implements OnInit {
  program!: Program;
  routineType = ROUTINE_TYPE;
  routinePlans!: RoutinePlan[];
  routines: (Routine | null)[] = [null,null,null,null,null,null,null];
  //subscription!: Subscription;
  weekDays: string[] = WEEK_DAYS;
  private _selectedWeekDay = 0;

  constructor(private routinePlanService: RoutinePlanService,
              public routineService: RoutineService,
              private navbarService: NavbarService,
              private programService: ProgramService) {
  }

  get selectedWeekDay(): number {
    return this._selectedWeekDay;
  }


  set selectedWeekDay(value: number) {
    this._selectedWeekDay = value;
    this.routinePlanService.selectedDay = value;
  }

  ngOnInit(): void {
    this.programService.program$.subscribe(program => {
      this.program = program[0]
    })
    this.programService.updateData()

    this.routinePlanService.weeklyRoutinePlanUrls = this.program.weeklyRoutinePlans;
    let urls: string[] = [];
    this.routinePlanService.individualRoutinePlans$.subscribe(routinePlans => {
      this.routinePlans = routinePlans
      this.routinePlans.forEach(rp => urls.push(rp.routine));
    })
    this.routinePlanService.updateData()
    this.routineService.routines$.subscribe(routines => {routines.forEach(routine => this.routines.push(routine));});
    this.routineService.routineUrls = urls;
  }

  removeRoutine() {
    if(this.routines[this.selectedWeekDay] != null){
      this.routineService.removeRoutine(this.routines[this.selectedWeekDay] as Routine);
    }
  }

}
