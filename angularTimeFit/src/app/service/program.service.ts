import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Program} from "../model/program.model";
import {environment} from "../../environments/environment";

const API = environment.apiEndpoint + 'api/program/';

@Injectable({
  providedIn: 'root'
})
export class ProgramService {

  private _program: BehaviorSubject<Program[]> = new BehaviorSubject<Program[]>([]);
  program$= this._program.asObservable();

  constructor(private http: HttpClient) {
    this.getProgram().subscribe(p => this._program.next(p));
  }

  getProgram(): Observable<Program[]>{
    return this.http.get<Program[]>(API);
  }

  updateProgram(program: Program){
    this.http.put(API, program).subscribe(a=>this.updateData());
  }

  updateData(){
    this.getProgram().subscribe(p => this._program.next(p));
  }
}
