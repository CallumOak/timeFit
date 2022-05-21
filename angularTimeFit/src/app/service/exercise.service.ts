import { Injectable } from '@angular/core';
import {Exercise} from "../model/exercise.model";
import {BehaviorSubject, Subject} from "rxjs";
import {RoutineService} from "./routine.service";
import { environment } from '../../environments/environment';
import {HttpClient} from "@angular/common/http";

const API = environment.apiEndpoint + 'api/exercises/';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  private exercises: Exercise[] = [];

  private _openedExercisesSource = new BehaviorSubject<Exercise[]>([]);

  openedExercises$ = this._openedExercisesSource.asObservable();

  constructor(private routineService: RoutineService, private http: HttpClient) { }

  getExercises(): Exercise[] {
    let exercises : Exercise[] = [];
    this.http.get<Exercise[]>(API).subscribe(e => exercises = e);
    return exercises;
  }

  getExerciseById(id: string): Exercise{
    let exercise = new Exercise();
    const path = API + id;
    this.http.get<Exercise>(path).subscribe(e => exercise = e);
    return exercise;
  }

  getExercise(uri: string): Exercise{
    let exercise = new Exercise();
    this.http.get<Exercise>(environment.apiEndpoint + uri).subscribe(e => exercise = e);
    return exercise;
  }

  createExercise(exercise: Exercise){
    this.http.post<Exercise>(API, exercise).subscribe(e => {
      exercise = e;
      this.updateData();
      this.routineService.updateData();
    });
    return exercise;
  }

  updateExercise(exercise: Exercise){
    this.http.put(API, exercise).subscribe(e => {
      this.updateData();
      this.routineService.updateData();
    });
  }

  deleteExercise(exercise: Exercise){
    this.http.delete(API + exercise.id).subscribe(e => this.updateData());
  }

  updateData(){
    this.exercises = this.getExercises();
    this._openedExercisesSource.next(this.exercises);
  }
}
