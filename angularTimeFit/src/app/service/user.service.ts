import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user.model';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

const API = environment.apiEndpoint + 'api/test/'

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any>{
    return this.http.get(API + 'all', {responseType: "text"});
  }
  getUserBoard(): Observable<any>{
    return this.http.get(API + 'user', {responseType: "text"});
  }
  getModeratorBoard(): Observable<any>{
    return this.http.get(API + 'mod', {responseType: "text"});
  }
  getAdminBoard(): Observable<any>{
    return this.http.get(API + 'admin', {responseType: "text"});
  }

}
