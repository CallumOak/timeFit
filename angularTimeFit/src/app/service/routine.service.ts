import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of, ReplaySubject} from "rxjs";
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

  private _availableRoutines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  availableRoutines$ = this._availableRoutines.asObservable();

  _routines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  _selectedRoutine: ReplaySubject<Routine> = new ReplaySubject<Routine>();

  routines$ = this._routines.asObservable();
  selectedRoutine$ = this._selectedRoutine.asObservable();

  constructor(private http: HttpClient, private exerciseService: ExerciseService) {
    this.updateData();
  }

  private getAvailableRoutines(): Observable<Routine[]>{
    return this.http.get<Routine[]>(API);
  }

  getRoutineById(selectedRoutineId: string): Observable<Routine> {
    const path = API + selectedRoutineId;
    return this.http.get<Routine>(path);
  }

  getRoutine(url: string): Observable<Routine> {
    return this.http.get<Routine>(environment.apiEndpoint + url);
  }

  getRoutines(){
    let routines: Routine[] = [];
    this._routineUrls.forEach(url => this.getRoutine(url).subscribe(r => routines.push(r)));
    this._routines.next(routines);
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
    this.http.put(API, routine).subscribe(r =>
      this.updateData());
  }

  removeRoutine(routine: Routine){
    this.http.delete<Routine>(API + routine.id).subscribe(this.updateData);
  }

  updateData(){
    this.getAvailableRoutines().subscribe(r => {
      this._availableRoutines.next(r);
      this.getRoutines();
    });
  }

  set routineUrls(value: string[]) {
    this._routineUrls = value;
    this.updateData();
  }
}
