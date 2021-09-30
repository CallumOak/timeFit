import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
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

  private _selectedDate = new Date();
  private _selectedDay = 0;
  private _selectedIndex = 0;

  selectedIndivRoutine$ = this.getIndivRoutine(this._selectedDate);
  selectedWeeklyRoutine$ = this.getWeeklyRoutine(this._selectedDay);
  selectedFrequencyRoutine$ = this.getFrequencyRoutine(this._selectedIndex);
  availableRoutines$ = this.getAvailableRoutines();
  frequencyRoutines$ = this.getFrequencyRoutines();
  weeklyRoutines$ = this.getWeeklyRoutines();

  constructor(private http: HttpClient) {
  }

  set selectedDate(date: Date) {
    this._selectedDate = date
    this.updateData()
  }

  set selectedDay(day: WeekDay) {
    this._selectedDay = day
    this.updateData()
  }

  set selectedIndex(index: number) {
    this._selectedIndex = index
    this.updateData()
  }


  private getIndivRoutine(date: Date): Observable<RoutinePlan> {
    const path = API + `individual/` + date;
    let response = this.http.get<RoutinePlan>(path);
    return response;
  }


  private getWeeklyRoutine(day: WeekDay): Observable<RoutinePlan>  {
    const path = API + `weekly/` + day;
    let response = this.http.get<RoutinePlan>(path);
    return response;
  }


  private getFrequencyRoutine(index: number): Observable<RoutinePlan>  {
    const path = API + `frequency/` + index;
    let response = this.http.get<RoutinePlan>(path);
    return response;
  }


  public setRoutine(type: RoutineTypeEnum, routine: Routine){
    switch (type){
      case RoutineTypeEnum.weekly:
        this.selectedWeeklyRoutine$.subscribe((routinePlan: RoutinePlan) => {
          routinePlan.routine = routine;
          this.http.put(API, routinePlan);
        });
        break;
      case RoutineTypeEnum.frequency:
        this.selectedFrequencyRoutine$.subscribe((routinePlan: RoutinePlan) => {
          routinePlan.routine = routine;
          this.http.put(API, routinePlan);
        });
        break;
      case RoutineTypeEnum.weekly:
        this.selectedIndivRoutine$.subscribe((routinePlan: RoutinePlan) => {
          routinePlan.routine = routine;
          this.http.put(API, routinePlan);
        });
        break;
    }
    this.updateData();
  }


  private getAvailableRoutines(): Observable<Routine[]>{
    const path = API;
    let response = this.http.get<Routine[]>(path);
    return response;
  }

  private getFrequencyRoutines(): Observable<RoutinePlan[]>{
    const path = API + ``;
    let response = this.http.get<RoutinePlan[]>(path);
    return response;
  }


  private getWeeklyRoutines(): Observable<RoutinePlan[]>{
    const path = API + ``;
    let response = this.http.get<RoutinePlan[]>(path);
    return response;
  }

  getRoutine(selectedRoutineId: string) {
    const path = API + selectedRoutineId;
    let response = this.http.get<RoutinePlan>(path);
    return response;
  }


  updateData(){
    this.selectedIndivRoutine$ = this.getIndivRoutine(this._selectedDate)
    this.selectedWeeklyRoutine$ = this.getWeeklyRoutine(this._selectedDay)
    this.selectedFrequencyRoutine$ = this.getFrequencyRoutine(this._selectedIndex)
    this.availableRoutines$ = this.getAvailableRoutines()
    this.frequencyRoutines$ = this.getFrequencyRoutines()
    this.weeklyRoutines$ = this.getWeeklyRoutines()
  }
}
