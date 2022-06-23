import {Component, OnDestroy, OnInit} from '@angular/core';
import {Routine} from "../../model/routine.model";
import {RoutineService} from "../../service/routine.service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";
import {NavbarService} from "../../service/navbar.service";
import {RoutinePlanService} from "../../service/routine-plan.service";
import {ExerciseService} from "../../service/exercise.service";

@Component({
  selector: 'app-routines',
  templateUrl: './routines.component.html',
  styleUrls: ['./routines.component.css']
})
export class RoutinesComponent implements OnInit, OnDestroy {
  routines!: Routine[];
  selectedRoutine?: Routine;
  private routineSubscription: Subscription = new Subscription();

  constructor(
          private routineService : RoutineService,
          private routinePlanService : RoutinePlanService,
          private navbarService: NavbarService,
          private router: Router,
          private exerciseService: ExerciseService
  ) {
    this.routineService.availableRoutines$.subscribe(rs => this.routines = rs);
  }

  ngOnInit(): void {
    this.routineSubscription = this.routineService.selectedRoutine$.subscribe(r =>{
      this.selectedRoutine = r;
    })
  }

  ngOnDestroy() {
    this.routineSubscription.unsubscribe();
  }

  select(i: number) {
    this.routineService.updateSelectedRoutine(this.routines[i]);
  }

  addRoutine() {
    let routine: Routine;
    this.routineService.createRoutine().subscribe(r => {
      routine = r;
      this.routineService.updateData();
      this.exerciseService.updateData();
      this.router.navigate(["/addEditRoutine/" + routine.id]);
    });
  }

  deleteRoutine() {
    if(this.selectedRoutine != undefined){
      this.routineService.removeRoutine(this.selectedRoutine);
      if(this.navbarService.getByName(this.selectedRoutine.name)){
        if( this.router.url.includes((this.navbarService.getByName(this.selectedRoutine.name)).path)){
          this.router.navigate(["/calendar"])
        }
        this.navbarService.removeItem(this.selectedRoutine.name);
      }
    }
  }
}
