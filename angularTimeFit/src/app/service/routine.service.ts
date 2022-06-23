import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of, ReplaySubject} from "rxjs";
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Routine} from '../model/routine.model';
import {Exercise} from "../model/exercise.model";
import {ExerciseService} from "./exercise.service";
import { RoutinePlanService } from './routine-plan.service';

const API = environment.apiEndpoint + '/api/routine/';

@Injectable({
  providedIn: 'root'
})
export class RoutineService {
  public static readonly emptyRoutine: Routine = new Routine();
  private _routineUrls: string[] = [];

  private _availableRoutines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  availableRoutines$ = this._availableRoutines.asObservable();

  _routines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  _selectedRoutine: ReplaySubject<Routine> = new ReplaySubject<Routine>();

  routines$ = this._routines.asObservable();
  selectedRoutine$ = this._selectedRoutine.asObservable();

  constructor(private http: HttpClient,
              private exerciseService: ExerciseService,
              private routinePlanService : RoutinePlanService) {
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
    this._routineUrls.forEach(url => {
      let idIndex = url.split("/").length - 1;
      let id = url.split("/")[idIndex];
      let routineIndex = this._availableRoutines.value.findIndex(r => r.id == id);
      if(routineIndex > -1 ){
        routines.push(this._availableRoutines.value[routineIndex]);
      }
    });
    this._routines.next(routines);
  }

  createRoutine(){
    let routine: Routine = new Routine();
    return this.http.post<Routine>(API, routine);
  }

  updateRoutine(routine: Routine){
    this.http.put(API, routine).subscribe(r => this.updateData());
  }

  removeRoutine(routine: Routine){
    this.http.delete<Routine>(API + routine.id).subscribe(r => {
      this.updateData();
      this.routinePlanService.updateData();
    });
  }

  updateData(){
    this.getAvailableRoutines().subscribe(r => {
      console.log("routines : " + r.length);
      this._availableRoutines.next(r);
      //this.getRoutines();
    });
  }

  set routineUrls(value: string[]) {
    this._routineUrls = value;
    this.updateData();
  }

  updateSelectedRoutine(routine: Routine){
    this._selectedRoutine.next(routine);
  }
}
