import { Injectable } from '@angular/core';
import {Exercise} from "../model/exercise.model";
import {BehaviorSubject, Observable, ReplaySubject, Subject} from "rxjs";
import {RoutineService} from "./routine.service";
import { environment } from '../../environments/environment';
import {HttpClient} from "@angular/common/http";

const API = environment.apiEndpoint + "/api/exercise/";

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {
  public emptyExercise = new Exercise();

  private _exercisesSource = new BehaviorSubject<Exercise[]>([]);
  exercises$ = this._exercisesSource.asObservable();

  private _selectedExercise = new ReplaySubject<Exercise>();
  selectedExercise$ = this._selectedExercise.asObservable();

  constructor(private http: HttpClient,
              private routineService : RoutineService) {
    this.emptyExercise.setValues("-1", "", 0, 0, "0", "#8d92dd", "#8d92dd", "", []);
  }

  getExercises(): Observable<Exercise[]> {
    return this.http.get<Exercise[]>(API);
  }

  getExerciseById(id: string): Observable<Exercise> {
    const path = API + id;
    return this.http.get<Exercise>(path);
  }

  getExercise(uri: string): Exercise{
    let exercise = new Exercise();
    this.http.get<Exercise>(environment.apiEndpoint + uri).subscribe(e => exercise = e);
    return exercise;
  }

  createExercise(){
    return this.http.post<Exercise>(API, this.emptyExercise);
  }

  updateExercise(exercise: Exercise){
    this.http.put(API, exercise).subscribe(e => {
      this.updateData();
    });
  }

  deleteExercise(exercise: Exercise){
    this.http.delete(API + exercise.id).subscribe(e => {
      this.updateData();
      this.routineService.updateData();
    });
  }

  selectExercise(id: string){
    this._exercisesSource.value.forEach(e=>{
        if(e.id == id) this._selectedExercise.next(e);
      }
    )
  }

  updateData(){
    this.getExercises().subscribe(e => {
      this._exercisesSource.next(e);
      this.selectedExercise$.subscribe(exercise =>{
        if(!exercise){
          this.selectExercise(e[0].id);
        }
      })
    });
  }
}
