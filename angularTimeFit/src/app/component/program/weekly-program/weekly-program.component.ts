import { Component, OnInit } from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {Subscription} from "rxjs";
import {NavbarService} from "../../../service/navbar.service";

const ROUTINE_TYPE = RoutineTypeEnum.weekly
const WEEK_DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

@Component({
  selector: 'app-weekly-program',
  templateUrl: './weekly-program.component.html',
  styleUrls: ['./weekly-program.component.css']
})
export class WeeklyProgramComponent implements OnInit {
  routineType = ROUTINE_TYPE;
  routines!: string[];
  subscription!: Subscription;
  weekDays: string[] = WEEK_DAYS;
  private _selectedWeekDay = 0;

  constructor(public routineService: RoutineService,
              private navbarService: NavbarService) {
  }

  get selectedWeekDay(): number {
    return this._selectedWeekDay;
  }

  set selectedWeekDay(value: number) {
    this._selectedWeekDay = value;
    this.routineService.selectedDay = value;
  }

  ngOnInit(): void {
    this.subscription = this.routineService.weeklyRoutines$.subscribe(routines => {
      this.routines = routines
    })
    this.routineService.updateData()
  }

  removeRoutine() {
    this.routineService.setRoutineBasedOnType(this.routineType, '')
  }
}
