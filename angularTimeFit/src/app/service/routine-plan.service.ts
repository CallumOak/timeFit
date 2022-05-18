import { Injectable } from '@angular/core';
import {RoutinePlan} from "../model/routine-plan.model";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {WeekDay} from "@angular/common";
import {RoutineTypeEnum} from "../enums/routine-type-enum.enum";
import {Routine} from "../model/routine.model";
import {WeeklyRoutinePlan} from "../model/weekly-routine-plan.model";
import {FrequencyRoutinePlan} from "../model/frequency-routine-plan.model";
import {IndividualRoutinePlan} from "../model/individual-routine-plan.model";

const API = environment.apiEndpoint + 'api/routinePlan/';

@Injectable({
  providedIn: 'root'
})
export class RoutinePlanService {

  private _weeklyRoutinePlanUrls: string[] = [];
  private _frequencyRoutinePlanUrls: string[] = [];
  private _individualRoutinePlanUrls: string[] = [];

  private _selectedDate = new Date();
  private _selectedDay = 0;
  private _selectedIndex = 0;

  frequencyRoutinePlans$ = this.getFrequencyRoutinePlans();
  weeklyRoutinePlans$ = this.getWeeklyRoutinePlans();
  individualRoutinePlans$ = this.getIndividualRoutinePlans();

  selectedIndivRoutine$ = this.getIndividualRoutinePlan(this._selectedDate);
  selectedWeeklyRoutine$ = this.getWeeklyRoutinePlan(this._selectedDay);
  selectedFrequencyRoutine$ = this.getFrequencyRoutinePlan(this._selectedIndex);

  constructor(private http: HttpClient) { }

  private getWeeklyRoutinePlans() : Observable<WeeklyRoutinePlan[]> {
    let routinePlans: WeeklyRoutinePlan[] = [];
    this._weeklyRoutinePlanUrls.forEach(url => this.http.get<WeeklyRoutinePlan>(environment.apiEndpoint + url)
      .subscribe(rp => routinePlans.push(rp)));
    return of(routinePlans);
  }

  private getFrequencyRoutinePlans(): Observable<FrequencyRoutinePlan[]>{
    let routinePlans: FrequencyRoutinePlan[] = [];
    this._frequencyRoutinePlanUrls.forEach(url => this.http.get<FrequencyRoutinePlan>(environment.apiEndpoint + url)
      .subscribe(rp => routinePlans.push(rp)));
    return of(routinePlans);
  }

  private getIndividualRoutinePlans(): Observable<IndividualRoutinePlan[]> {
    let routinePlans: IndividualRoutinePlan[] = [];
    this._individualRoutinePlanUrls.forEach(url => this.http.get<IndividualRoutinePlan>(environment.apiEndpoint + url)
      .subscribe(rp => routinePlans.push(rp)));
    return of(routinePlans);
  }

  private getWeeklyRoutinePlan(day: WeekDay): Observable<WeeklyRoutinePlan>  {
    let weeklyRoutinePlans: WeeklyRoutinePlan[] =[];
    let weeklyRoutinePlan: WeeklyRoutinePlan;
    this.weeklyRoutinePlans$.subscribe(rps => weeklyRoutinePlans = rps);
    weeklyRoutinePlan = weeklyRoutinePlans[weeklyRoutinePlans.findIndex(rp => rp.day === day)]
    return of(weeklyRoutinePlan);
  }

  private getFrequencyRoutinePlan(index: number): Observable<FrequencyRoutinePlan>  {
    let frequencyRoutinePlans: FrequencyRoutinePlan[] =[];
    let frequencyRoutinePlan: FrequencyRoutinePlan;
    this.frequencyRoutinePlans$.subscribe(rps => frequencyRoutinePlans = rps);
    frequencyRoutinePlan = frequencyRoutinePlans[frequencyRoutinePlans.findIndex(rp => rp.index === index)]
    return of(frequencyRoutinePlan);
  }

  private getIndividualRoutinePlan(date: Date): Observable<IndividualRoutinePlan>  {
    let individualRoutinePlans: IndividualRoutinePlan[] =[];
    let individualRoutinePlan: IndividualRoutinePlan;
    this.individualRoutinePlans$.subscribe(rps => individualRoutinePlans = rps);
    individualRoutinePlan = individualRoutinePlans[individualRoutinePlans.findIndex(rp => rp.date === date)]
    return of(individualRoutinePlan);
  }

  public setRoutine(type: RoutineTypeEnum, routine: Routine){
    switch (type){
      case RoutineTypeEnum.weekly:
        this.selectedWeeklyRoutine$.subscribe((routinePlan: RoutinePlan) => {
          routinePlan.routine = API + "routine/" + routine.id;
          this.http.put(API, routinePlan);
        });
        break;
      case RoutineTypeEnum.frequency:
        this.selectedFrequencyRoutine$.subscribe((routinePlan: RoutinePlan) => {
          routinePlan.routine = API + "routine/" + routine.id;
          this.http.put(API, routinePlan);
        });
        break;
      case RoutineTypeEnum.individual:
        this.selectedIndivRoutine$.subscribe((routinePlan: RoutinePlan) => {
          routinePlan.routine = API + "routine/" + routine.id;
          this.http.put(API, routinePlan);
        });
        break;
    }
    this.updateData();
  }

  public updateData() {
    this.selectedIndivRoutine$ = this.getIndividualRoutinePlan(this._selectedDate)
    this.selectedWeeklyRoutine$ = this.getWeeklyRoutinePlan(this._selectedDay)
    this.selectedFrequencyRoutine$ = this.getFrequencyRoutinePlan(this._selectedIndex)
    this.individualRoutinePlans$ = this.getIndividualRoutinePlans();
    this.weeklyRoutinePlans$ = this.getWeeklyRoutinePlans();
    this.frequencyRoutinePlans$ = this.getFrequencyRoutinePlans();
  }

  public get weeklyRoutinePlanUrls(): string[] {
    return this._weeklyRoutinePlanUrls;
  }

  public set weeklyRoutinePlanUrls(value: string[]) {
    this._weeklyRoutinePlanUrls = value;
    this.updateData();
  }

  get individualRoutinePlanUrls(): string[] {
    return this._individualRoutinePlanUrls;
  }

  set individualRoutinePlanUrls(value: string[]) {
    this._individualRoutinePlanUrls = value;
    this.updateData();
  }

  get frequencyRoutinePlanUrls(): string[] {
    return this._frequencyRoutinePlanUrls;
  }

  set frequencyRoutinePlanUrls(value: string[]) {
    this._frequencyRoutinePlanUrls = value;
    this.updateData();
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
}
