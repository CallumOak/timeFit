import { Component, OnInit } from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {Subscription} from "rxjs";
import {Routine} from "../../../model/routine.model";
import {RoutinePlanService} from "../../../service/routine-plan.service";
import {FrequencyRoutinePlan} from "../../../model/frequency-routine-plan.model";

const ROUTINE_TYPE = RoutineTypeEnum.frequency

@Component({
  selector: 'app-frequency-program',
  templateUrl: './frequency-program.component.html',
  styleUrls: ['./frequency-program.component.css']
})
export class FrequencyProgramComponent implements OnInit {
  routineType = ROUTINE_TYPE;
  routines!: Routine[];
  frequencyRoutinePlans : FrequencyRoutinePlan[] = [];
  subscription!: Subscription;
  private _selectedIndex: number = 0;

  constructor(public routinePlanService: RoutinePlanService, public routineService: RoutineService) {
  }

  get selectedIndex(): number {
    return this._selectedIndex;
  }

  set selectedIndex(value: number) {
    this._selectedIndex = value;
    this.routinePlanService.selectedIndex = value;
  }

  ngOnInit(): void {
    this.subscription = this.routinePlanService.frequencyRoutinePlans$.subscribe(routines => {
      this.frequencyRoutinePlans = routines
    })
    this.routinePlanService.updateData()
  }

  canAddRoutine(i: number){
    return i < this.routines.length && this.routines[i] == null && (i == 0 || this.routines[i - 1] != null);
  }

  removeRoutine() {
    this.routinePlanService.setRoutine(this.routineType, this.routines[this.selectedIndex])
  }
}
