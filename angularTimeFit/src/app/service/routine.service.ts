import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of} from "rxjs";
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Routine} from '../model/routine.model';
import {Exercise} from "../model/exercise.model";
import {ExerciseService} from "./exercise.service";

const API = environment.apiEndpoint + '/api/routine/';

@Injectable({
  providedIn: 'root'
})
export class RoutineService {
  private _routineUrls: string[] = [];
  _availableRoutines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  _routines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  availableRoutines$ = this._availableRoutines.asObservable();
  routines$ = this._routines.asObservable();


  constructor(private http: HttpClient, private exerciseService: ExerciseService) {
    this.updateData();
  }

  private getAvailableRoutines(): Observable<Routine[]>{
    return this.http.get<Routine[]>(API);
  }

  getRoutineById(selectedRoutineId: string) {
    const path = API + selectedRoutineId;
    return this.http.get<Routine>(path);
  }

  getRoutine(url: string) {
    return this.http.get<Routine>(environment.apiEndpoint + url);
  }

  getRoutines(): Observable<Routine[]> {
    let routines: Routine[] = [];
    this._routineUrls.forEach(url => this.getRoutine(url).subscribe(r => routines.push(r)));
    return of(routines);
  }

  createRoutine(routine: Routine){
    this.http.post<Routine>(API, routine).subscribe(r => {
      routine = r;
      this.updateData();
      this.exerciseService.updateData();
    });
    return routine;
  }

  updateRoutine(routine: Routine){
    this.http.put(API, routine).subscribe(r => {
      this.updateData();
      this.exerciseService.updateData();
    });
  }

  removeRoutine(routine: Routine){
    this.http.delete<Routine>(API + routine.id).subscribe(this.updateData);
  }

  updateData(){
    this.getRoutines().subscribe(r => this._routines.next(r));
    this.getAvailableRoutines().subscribe(r => this._availableRoutines.next(r));
  }

  set routineUrls(value: string[]) {
    this._routineUrls = value;
    this.updateData();
  }
}
