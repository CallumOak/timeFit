import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserListComponent} from "./component/user-list/user-list.component";
import {UserFormComponent} from "./component/user-form/user-form.component";
import {HomeComponent} from "./component/home/home.component";
import {LoginComponent} from "./component/login/login.component";
import {ProfileComponent} from "./component/profile/profile.component";
import {BoardUserComponent} from "./component/board-user/board-user.component";
import {BoardModeratorComponent} from "./component/board-moderator/board-moderator.component";
import {BoardAdminComponent} from "./component/board-admin/board-admin.component";
import {SignupComponent} from "./component/signup/signup.component";
import {CalendarComponent} from "./component/calendar/calendar.component";

const routes: Routes = [
  {path: "users", component: UserListComponent},
  {path: "adduser", component: UserFormComponent},
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: SignupComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'calendar', component: CalendarComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
