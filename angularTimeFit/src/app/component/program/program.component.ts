import { Component, OnInit } from '@angular/core';
import {RoutineTypeEnum} from "../../enums/routine-type-enum.enum";
import {RoutineService} from "../../service/routine.service";
import {Router} from "@angular/router";
import {Routine} from "../../model/routine.model";
import {RoutinePlan} from "../../model/routine-plan.model";

const ROUTINE_TYPE = RoutineTypeEnum.individual

@Component({
  selector: 'app-program',
  templateUrl: './program.component.html',
  styleUrls: ['./program.component.css']
})
export class ProgramComponent implements OnInit {
  routineType = ROUTINE_TYPE
  private _selectedDate: Date = new Date()

  constructor(public routineService: RoutineService, public router: Router) {
  }

  get selectedDate(): Date {
    return this._selectedDate;
  }

  set selectedDate(date: Date) {
    this._selectedDate = date;
    this.routineService.selectedDate = date;
  }

  ngOnInit(): void {
  }

  removeRoutine() {
    this.routineService.selectedIndivRoutine$.subscribe(
      r => this.routineService.setRoutine(this.routineType, r.routine)
    )
  }
}
