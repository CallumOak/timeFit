import { Injectable } from '@angular/core';
import {Observable, Subject} from "rxjs";
import {WeekDay} from "@angular/common";
import {RoutineTypeEnum} from "../enums/routine-type-enum.enum";

@Injectable({
  providedIn: 'root'
})
export class RoutineService {
  //inteface mocking properties, to be removed when interface is implemented
  private ar = ['Upper body', 'Lower body', 'Cardio'];
  private wrs = ['Upper body', 'Lower body', '', '', '', '', ''];
  private frs = ['Cardio', 'Cardio', '', '', ''];
  private ir = 'Indiv routine';
  private wr = 'Weekly routine';
  private fr = 'Frequency routine';

  private _selectedDate = new Date();
  private _selectedDay = 0;
  private _selectedIndex = 0;

  private _selectedIndivRoutineSource = new Subject<string>();
  private _selectedWeeklyRoutineSource = new Subject<string>();
  private _selectedFrequencyRoutineSource = new Subject<string>();
  private _availableRoutinesSource = new Subject<string[]>();
  private _frequencyRoutines = new Subject<string[]>();
  private _weeklyRoutines = new Subject<string[]>();

  selectedIndivRoutine$ = this._selectedIndivRoutineSource.asObservable();
  selectedWeeklyRoutine$ = this._selectedWeeklyRoutineSource.asObservable();
  selectedFrequencyRoutine$ = this._selectedFrequencyRoutineSource.asObservable();
  availableRoutines$ = this._availableRoutinesSource.asObservable();
  frequencyRoutines$ = this._frequencyRoutines.asObservable();
  weeklyRoutines$ = this._weeklyRoutines.asObservable();

  constructor() {
  }

  set selectedDate(date: Date) {
    this._selectedDate = date;
    this._selectedIndivRoutineSource.next(this.getIndivRoutine(date));
  }

  set selectedDay(day: WeekDay) {
    this._selectedDay = day;
    this._selectedWeeklyRoutineSource.next(this.getWeeklyRoutine(day));
  }

  set selectedIndex(index: number) {
    this._selectedIndex = index;
    this._selectedFrequencyRoutineSource.next(this.getFrequencyRoutine(index));
  }


//TODO: implement call to interface
  private getIndivRoutine(date: Date): string {
    return this.ir;
  }

//TODO: implement call to interface
  private setIndivRoutine(routine: string){
    this.ir = routine
  }

//TODO: implement call to interface
  private getWeeklyRoutine(day: WeekDay) {
    this.wr = this.wrs[day]
    return this.wr;
  }

//TODO: implement call to interface
  private setWeeklyRoutine(routine: string){
    this.wr = routine
    this.wrs[this._selectedDay] = routine
  }

//TODO: implement call to interface
  private getFrequencyRoutine(index: number) {
    this.fr = this.frs[index]
    return this.fr;
  }

//TODO: implement call to interface
  private setFrequencyRoutine(routine: string){
    if(routine != '' || this._selectedIndex == this.frs.length - 1 || this.frs[this._selectedIndex + 1] == ''){
      this.fr = routine
      this.frs[this._selectedIndex] = routine
    }
    else{
      let tmpIndex = this._selectedIndex + 1;
      let prevTmp = this._selectedIndex;
      while(tmpIndex < this.frs.length && this.frs[tmpIndex] != ''){
        this.frs[prevTmp] = this.frs[tmpIndex];
        this.frs[tmpIndex] = '';
        tmpIndex += 1;
        prevTmp += 1;
      }
    }
  }

//TODO: implement call to interface
  private getAvailableRoutines(): string[]{
    return this.ar;
  }

//TODO: implement call to interface
  private getfFrequencyRoutines(): string[]{
    return this.frs;
  }

//TODO: implement call to interface
  private getWeeklyRoutines(): string[]{
    return this.wrs;
  }

  getRoutineBasedOnType(routineType: RoutineTypeEnum): Observable<string>{
    console.log(`Get routine type : ${routineType}`)
    switch(routineType){
      case RoutineTypeEnum.individual: {
        return this.selectedIndivRoutine$
        break
      }
      case RoutineTypeEnum.weekly: {
        return this.selectedWeeklyRoutine$
        break
      }
      case RoutineTypeEnum.frequency: {
        return this.selectedFrequencyRoutine$
        break
      }
      default:{
        throw new Error(`Routine type not found : ${routineType}`)
      }
    }
  }

  setRoutineBasedOnType(routineType: RoutineTypeEnum, routine: string){
    switch(routineType){
      case RoutineTypeEnum.individual: {
        this.setIndivRoutine(routine)
        this.updateData()
        break
      }
      case RoutineTypeEnum.weekly: {
        this.setWeeklyRoutine(routine)
        this.updateData()
        break
      }
      case RoutineTypeEnum.frequency:{
        this.setFrequencyRoutine(routine)
        this.updateData()
        break
      }
      default:{
        throw new Error(`Routine type not found : ${routineType}`)
      }
    }
  }

  updateData(){
    this._selectedIndivRoutineSource.next(this.getIndivRoutine(this._selectedDate))
    this._selectedWeeklyRoutineSource.next(this.getWeeklyRoutine(this._selectedDay))
    this._selectedFrequencyRoutineSource.next(this.getFrequencyRoutine(this._selectedIndex))
    this._availableRoutinesSource.next(this.getAvailableRoutines())
    this._frequencyRoutines.next(this.getfFrequencyRoutines())
    this._weeklyRoutines.next(this.getWeeklyRoutines())
  }

}
