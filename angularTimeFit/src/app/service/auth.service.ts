import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import { environment } from '../../environments/environment';

const API = environment.apiEndpoint + '/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any>{
    return this.http.post(
      API + 'signin',
      {
        username,
        password
      },
      httpOptions)
  }

  signup(username: string, email: string, password: string): Observable<any>{
    return this.http.post(
      API + 'signup',
      {
        username,
        email,
        password
      },
      httpOptions
    )
  }

}
