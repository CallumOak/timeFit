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
import {WorkoutComponent} from "./component/workout/workout.component";
import {RoutinesComponent} from "./component/routines/routines.component";
import {ExercisesComponent} from "./component/exercises/exercises.component";
import {ProgramComponent} from "./component/program/program.component";
import {AddEditRoutineComponent} from "./component/add-edit-routine/add-edit-routine.component";
import {AddEditExerciseComponent} from "./component/add-edit-exercise/add-edit-exercise.component";

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
  { path: 'workout', component: WorkoutComponent },
  { path: 'routines', component: RoutinesComponent },
  { path: 'exercises', component: ExercisesComponent },
  { path: 'program', component: ProgramComponent },
  { path: 'addEditRoutine', component: AddEditRoutineComponent },
  { path: 'addEditExercise', component: AddEditExerciseComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
