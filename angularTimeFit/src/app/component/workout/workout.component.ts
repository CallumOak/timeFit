import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Exercise} from "../../model/exercise.model";
import {Subscription} from "rxjs";
import {ExerciseService} from 'src/app/service/exercise.service';
import {Routine} from "../../model/routine.model";
import {RoutineService} from 'src/app/service/routine.service';
import {CountdownComponent, CountdownConfig, CountdownEvent} from "ngx-countdown";
import { ActivatedRoute } from '@angular/router';
import {RoutineVisualizerComponent} from "../routine-visualizer/routine-visualizer.component";

@Component({
  selector: 'app-workout',
  templateUrl: './workout.component.html',
  styleUrls: ['./workout.component.css']
})
export class WorkoutComponent implements  OnDestroy, AfterViewInit {
  @ViewChild(RoutineVisualizerComponent)
  routineVisualizer!: RoutineVisualizerComponent;
  @ViewChild(CountdownComponent)
  countdown!: CountdownComponent;
  exercises: Exercise[] = [];
  runningExerciseIndex: number = 0;
  runningExercise(){
    if(this.routineVisualizer
      && this.routineVisualizer.exercises
      && this.routineVisualizer.exercises[this.runningExerciseIndex]){
      return this.routineVisualizer.exercises[this.runningExerciseIndex];
    }
    return this.exerciseService.emptyExercise;
  };
  rest: boolean = false;
  availableRoutines!: Routine[];
  private _selectedRoutine: Routine = RoutineService.emptyRoutine;
  selectedExercises: Exercise[] = [];
  exerciseSubscription!: Subscription;
  routineSubscriptions!: Subscription;
  config: CountdownConfig = {leftTime: 60,
    format: 'HH:mm:ss',
    prettyText: (text) => {
      return text
        .split(':')
        .map((v) => `<span class="item">${v}</span>`)
        .join('');
    },
  };

  constructor(public exerciseService: ExerciseService,
              private routineService: RoutineService,
              private activatedRoute: ActivatedRoute) {
    }

  set selectedRoutine(value: Routine) {
    if(value == undefined) {
      value = RoutineService.emptyRoutine;
    }
    this._selectedRoutine = value;
    this.routineVisualizer.routine = value;
    this.selectedExercises = [];
    this._selectedRoutine.exercises.forEach(eUrl => {
      let id = this.getId(eUrl);
      let index = this.exercises.findIndex(e => e.id == id);
      this.selectedExercises.push(this.exercises[index]);
    })
    this.orderExercises();
    this.resetRoutine()
  }

  get selectedRoutine() { return this._selectedRoutine; }

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

  ngOnDestroy(): void {
    this.exerciseSubscription.unsubscribe();
  }

  handleEvent(e: CountdownEvent) {
    if(e.action == "done"){
      if(this.runningExerciseIndex < this.selectedRoutine.exercises.length - 1 || !this.rest){
        if(this.rest){
          this.runningExerciseIndex += 1;
          this.rest = false;
        }
        else(
          this.rest = true
        )
        this.resetCountdown();
        this.countdown.begin();
      }
      else{
        this.resetRoutine();
      }
    }
  }

  resetRoutine() {
    this.runningExerciseIndex = 0;
    this.rest = false;
    this.resetCountdown();
  }

  ngAfterViewInit() {
    this.countdown.pause();
    this.routineService.availableRoutines$.subscribe(rs => {
        this.availableRoutines = rs;
        let selectedRoutineId = this.activatedRoute.snapshot.params['id'];
        console.log(selectedRoutineId);
        let selectedRoutineIndex = selectedRoutineId != undefined ?
          this.availableRoutines.findIndex(r => r.id == selectedRoutineId) : 0;
        console.log(selectedRoutineIndex);
        this.selectedRoutine = this.availableRoutines[selectedRoutineIndex];
      }
    )
    this.routineService.availableRoutines$.subscribe(rs => {
        this.availableRoutines = rs;
      }
    )
    this.exerciseSubscription = this.exerciseService.exercises$.subscribe(es => this.exercises = es);
  }

  resetCountdown() {
    if(this.routineVisualizer != undefined && this.countdown != undefined){
      if(this.routineVisualizer.exercises != undefined && this.routineVisualizer.exercises.length > 0){
        this.config.leftTime = this.rest ? +this.runningExercise().exerciseBreak : +this.runningExercise().exerciseDuration;
        this.countdown.config = this.config;
      }
      this.countdown.restart();
      this.countdown.pause();
    }

  }
}
