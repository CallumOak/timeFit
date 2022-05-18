import { Injectable } from '@angular/core';
import {Exercise} from "../model/exercise.model";
import {Subject} from "rxjs";
import {RoutineService} from "./routine.service";
import { environment } from '../../environments/environment';
import {HttpClient} from "@angular/common/http";

const API = environment.apiEndpoint + 'exercises/';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  private exercises: Exercise[] = new Array();

  private _openedExercisesSource = new Subject<Exercise[]>();

  openedExercises$ = this._openedExercisesSource.asObservable();

  constructor(private routineService: RoutineService, private http: HttpClient) { }

  getExercise(id: string): Exercise{
    let exercise = new Exercise();
    this.exercises.forEach(e => {
      if(e.id === id){
        exercise = e;
      }
    })
    if(exercise.id === ''){
      //TODO replace with http request

      const path = API + id;
      this.http.get<Exercise>(path).subscribe(e => exercise = e);


      this.exercises.push(exercise);
      this._openedExercisesSource.next(this.exercises);
    }
    return exercise;
  }

  updateExercise(exercise: Exercise){
    let index = this.exercises.indexOf(exercise);
    if(index === -1){
      let selectedExercise = this.createExercise(exercise);
      this.exercises.push(selectedExercise);
    }
    else{
      //TODO http request to do update
      this.exercises[index] = exercise;
    }
    this._openedExercisesSource.next(this.exercises);
    this.routineService.updateData();
  }

  createExercise(exercise: Exercise): Exercise{
    let newExercise = new Exercise();
    newExercise.setValues(
      exercise.id,
      exercise.name,
      exercise.exerciseDuration,
      exercise.restDuration,
      exercise.repetitions,
      exercise.exerciseColor,
      exercise.restColor,
      exercise.illustrationLocation)
    this.http.post<Exercise>(API, newExercise).subscribe(e => newExercise = e);
    this.routineService.updateData();
    return newExercise;
  }

  deleteExercise(exercise: Exercise){
    this.exercises.pop();

  }
}
