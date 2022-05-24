import { Component, OnInit } from '@angular/core';
import {Routine} from "../../model/routine.model";
import {RoutineService} from "../../service/routine.service";

@Component({
  selector: 'app-routines',
  templateUrl: './routines.component.html',
  styleUrls: ['./routines.component.css']
})
export class RoutinesComponent implements OnInit {
  routines!: Routine[];

  constructor(private routineService : RoutineService) {
    this.routineService.availableRoutines$.subscribe(rs => this.routines = rs);
  }

  ngOnInit(): void {
  }

}
