import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of} from "rxjs";
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Routine} from '../model/routine.model';

const API = environment.apiEndpoint + 'routines/';

@Injectable({
  providedIn: 'root'
})
export class RoutineService {
  private _routineUrls: string[] = [];
  _availableRoutines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  _routines: BehaviorSubject<Routine[]> = new BehaviorSubject<Routine[]>([]);
  availableRoutines$ = this._availableRoutines.asObservable();
  routines$ = this._routines.asObservable();


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
    this.getRoutines().subscribe(r => this._routines.next(r));
    this.getAvailableRoutines().subscribe(r => this._availableRoutines.next(r));
  }

  set routineUrls(value: string[]) {
    this._routineUrls = value;
  }
}
