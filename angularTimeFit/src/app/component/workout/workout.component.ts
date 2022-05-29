import {Component, OnDestroy, OnInit} from '@angular/core';
import {Exercise} from "../../model/exercise.model";
import {Subscription} from "rxjs";
import {ExerciseService} from 'src/app/service/exercise.service';
import {Routine} from "../../model/routine.model";
import {RoutineService} from 'src/app/service/routine.service';

@Component({
  selector: 'app-workout',
  templateUrl: './workout.component.html',
  styleUrls: ['./workout.component.css']
})
export class WorkoutComponent implements OnInit, OnDestroy {
  exercises: Exercise[] = [];
  runningExerciseIndex: number = 0;
  runningExercise!: Exercise;
  availableRoutines!: Routine[];
  private _selectedRoutine!: Routine;
  selectedExercises: Exercise[] = [];
  exerciseSubscription!: Subscription;
  routineSubscriptions!: Subscription;

  constructor(private exerciseService: ExerciseService,
              private routineService: RoutineService) { }

  set selectedRoutine(value: Routine) {
    this._selectedRoutine = value;
    this.selectedExercises = [];
    this._selectedRoutine.exercises.forEach(eUrl => {
      let id = this.getId(eUrl);
      let index = this.exercises.findIndex(e => e.id == id);
      this.selectedExercises.push(this.exercises[index]);
    })
    this.orderExercises();
  }

  private orderExercises() {
    this.selectedExercises.sort((a, b) => this.getRelevantPosition(a) - this.getRelevantPosition(b));
  }

  private getRelevantPosition(exercise: Exercise): number{
    return exercise.routines.findIndex(rUrl => {
      let id = this.getId(rUrl);
      return this._selectedRoutine.id == id;
    });
  }

  private getId(url: string): string{
    return url.split('/')[url.split('/').length - 1];
  }

  ngOnInit(): void {
    this.routineService.availableRoutines$.subscribe(rs => {
        this.availableRoutines = rs;
      }
    )
    this.exerciseSubscription = this.exerciseService.exercises$.subscribe(es => this.exercises = es);
  }

  ngOnDestroy(): void {
    this.exerciseSubscription.unsubscribe();
  }
}
