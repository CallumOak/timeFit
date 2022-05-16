import { Component, OnInit } from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {Subscription} from "rxjs";
import {Routine} from "../../../model/routine.model";

const ROUTINE_TYPE = RoutineTypeEnum.frequency

@Component({
  selector: 'app-frequency-program',
  templateUrl: './frequency-program.component.html',
  styleUrls: ['./frequency-program.component.css']
})
export class FrequencyProgramComponent implements OnInit {
  routineType = ROUTINE_TYPE;
  routines!: Routine[];
  subscription!: Subscription;
  private _selectedIndex: number = 0;

  constructor(public routineService: RoutineService) {
  }

  get selectedIndex(): number {
    return this._selectedIndex;
  }

  set selectedIndex(value: number) {
    this._selectedIndex = value;
    this.routineService.selectedIndex = value;
  }

  ngOnInit(): void {
    this.subscription = this.routineService.frequencyRoutines$.subscribe(routines => {
      this.routines = routines
    })
    this.routineService.updateData()
  }

  canAddRoutine(i: number){
    return i < this.routines.length && this.routines[i] == null && (i == 0 || this.routines[i - 1] != null);
  }

  removeRoutine() {
    this.routineService.setRoutine(this.routineType, this.routines[this.selectedIndex])
  }
}
