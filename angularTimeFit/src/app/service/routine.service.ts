import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {WeekDay} from "@angular/common";
import {RoutineTypeEnum} from "../enums/routine-type-enum.enum";
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Routine} from '../model/routine.model';
import {RoutinePlan} from "../model/routine-plan.model";

const API = environment.apiEndpoint + 'routines/';

@Injectable({
  providedIn: 'root'
})
export class RoutineService {
  private _routineUrls: string[] = [];
  availableRoutines$ = this.getAvailableRoutines();
  routines$ = this.getRoutines();


  constructor(private http: HttpClient) {
  }

  private getAvailableRoutines(): Observable<Routine[]>{
    return this.http.get<Routine[]>(API);
  }

  getRoutineById(selectedRoutineId: string) {
    const path = API + selectedRoutineId;
    return this.http.get<Routine>(path);
  }

  getRoutine(url: string) {
    return this.http.get<Routine>(url);
  }

  getRoutines(): Observable<Routine[]> {
    let routines: Routine[] = [];
    this._routineUrls.forEach(url => this.getRoutine(url).subscribe(r => routines.push(r)));
    return of(routines);
  }

  removeRoutine(routine: Routine){
    this.http.delete<Routine>(API + routine.id).subscribe(this.updateData);
  }

  updateData(){
    this.availableRoutines$ = this.getAvailableRoutines()
  }

  get routineUrls(): string[] {
    return this._routineUrls;
  }

  set routineUrls(value: string[]) {
    this._routineUrls = value;
  }
}
