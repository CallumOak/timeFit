import { Injectable } from '@angular/core';
import {RoutinePlan} from "../model/routine-plan.model";
import {BehaviorSubject, Observable, of, ReplaySubject} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {WeekDay} from "@angular/common";
import {RoutineTypeEnum} from "../enums/routine-type-enum.enum";
import {Routine} from "../model/routine.model";
import {WeeklyRoutinePlan} from "../model/weekly-routine-plan.model";
import {FrequencyRoutinePlan} from "../model/frequency-routine-plan.model";
import {IndividualRoutinePlan} from "../model/individual-routine-plan.model";
import { ProgramService } from './program.service';

const API = environment.apiEndpoint + 'api/routinePlan/';

@Injectable({
  providedIn: 'root'
})
export class RoutinePlanService {
  private update: boolean = true;

  private _weeklyRoutinePlanUrls: string[] = [];
  private _frequencyRoutinePlanUrls: string[] = [];
  private _individualRoutinePlanUrls: string[] = [];

  private _selectedDate = new Date();
  private _selectedDay = 0;
  private _selectedIndex = 0;

  private _weeklyRoutinePlans: BehaviorSubject<WeeklyRoutinePlan[]> = new BehaviorSubject<WeeklyRoutinePlan[]>([]);
  private _frequencyRoutinePlans: BehaviorSubject<FrequencyRoutinePlan[]> = new BehaviorSubject<FrequencyRoutinePlan[]>([]);
  private _individualRoutinePlans: BehaviorSubject<IndividualRoutinePlan[]> = new BehaviorSubject<IndividualRoutinePlan[]>([]);

  weeklyRoutinePlans$ = this._weeklyRoutinePlans.asObservable();
  frequencyRoutinePlans$ = this._frequencyRoutinePlans.asObservable();
  individualRoutinePlans$ = this._individualRoutinePlans.asObservable();

  private _selectedWeeklyRoutine: ReplaySubject<WeeklyRoutinePlan> = new ReplaySubject<WeeklyRoutinePlan>();
  private _selectedFrequencyRoutine: ReplaySubject<FrequencyRoutinePlan> = new ReplaySubject<FrequencyRoutinePlan>();
  private _selectedIndivRoutine: ReplaySubject<IndividualRoutinePlan> = new ReplaySubject<IndividualRoutinePlan>();

  selectedIndivRoutine$ = this._selectedIndivRoutine.asObservable();
  selectedWeeklyRoutine$ = this._selectedWeeklyRoutine.asObservable();
  selectedFrequencyRoutine$ = this._selectedFrequencyRoutine.asObservable();

  constructor(private http: HttpClient, private programService: ProgramService) {
    this.programService.program$.subscribe(p => {
      this.update = false;
      this.weeklyRoutinePlanUrls = p[0].weeklyRoutinePlans;
      this.frequencyRoutinePlanUrls = p[0].frequencyRoutinePlans;
      this.update = true;
      this.individualRoutinePlanUrls = p[0].individualRoutinePlans;
    })
  }

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
    this.getWeeklyRoutinePlan(this._selectedDay).subscribe(rp => this._selectedWeeklyRoutine.next(rp));
    this.getFrequencyRoutinePlan(this._selectedIndex).subscribe(rp => this._selectedFrequencyRoutine.next(rp));
    this.getIndividualRoutinePlan(this._selectedDate).subscribe(rp => this._selectedIndivRoutine.next(rp));
    this.getWeeklyRoutinePlans().subscribe(rp => this._weeklyRoutinePlans.next(rp));
    this.getFrequencyRoutinePlans().subscribe(rp => this._frequencyRoutinePlans.next(rp));
    this.getIndividualRoutinePlans().subscribe(rp => this._individualRoutinePlans.next(rp));
  }

  public set weeklyRoutinePlanUrls(value: string[]) {
    this._weeklyRoutinePlanUrls = value;
    if(this.update)this.updateData();
  }

  set individualRoutinePlanUrls(value: string[]) {
    this._individualRoutinePlanUrls = value;
    if(this.update)this.updateData();
  }

  set frequencyRoutinePlanUrls(value: string[]) {
    this._frequencyRoutinePlanUrls = value;
    if(this.update)this.updateData();
  }

  set selectedDate(date: Date) {
    this._selectedDate = date
    if(this.update)this.updateData()
  }

  set selectedDay(day: WeekDay) {
    this._selectedDay = day
    if(this.update)this.updateData()
  }

  set selectedIndex(index: number) {
    this._selectedIndex = index
    if(this.update)this.updateData()
  }
}
