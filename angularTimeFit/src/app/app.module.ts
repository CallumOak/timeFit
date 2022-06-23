import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './component/user-list/user-list.component';
import { UserFormComponent } from './component/user-form/user-form.component';
import { BoardAdminComponent } from './component/board-admin/board-admin.component';
import { BoardUserComponent } from './component/board-user/board-user.component';
import { BoardModeratorComponent } from './component/board-moderator/board-moderator.component';
import { HomeComponent } from './component/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { ProfileComponent } from './component/profile/profile.component';
import { SignupComponent } from './component/signup/signup.component';

import { authInterceptorProviders } from './helper/auth.interceptor';

import {FullCalendarModule} from '@fullcalendar/angular';
import interactionPlugin from '@fullcalendar/interaction';
import rrulePlugin from '@fullcalendar/rrule';
import dayGridPlugin from '@fullcalendar/daygrid';
import { CalendarComponent } from './component/calendar/calendar.component';
import { ProgramComponent } from './component/program/program.component';
import { RoutinesComponent } from './component/routines/routines.component';
import { ExercisesComponent } from './component/exercises/exercises.component';
import { WorkoutComponent } from './component/workout/workout.component';
import { AddEditRoutineComponent } from './component/add-edit-routine/add-edit-routine.component';
import { AddEditExerciseComponent } from './component/add-edit-exercise/add-edit-exercise.component';
import { WeeklyProgramComponent } from './component/program/weekly-program/weekly-program.component';
import { FrequencyProgramComponent } from './component/program/frequency-program/frequency-program.component';
import { RoutineVisualizerComponent } from './component/routine-visualizer/routine-visualizer.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { DialogExerciseSelectComponent } from './component/dialog-exercise-select/dialog-exercise-select.component';
import { RoutineCardComponent } from './component/program/routine-card/routine-card.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {DragDropModule} from  '@angular/cdk/drag-drop';
import { CountdownModule } from 'ngx-countdown';

FullCalendarModule.registerPlugins([
  interactionPlugin,
  dayGridPlugin,
  rrulePlugin
]);

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    BoardAdminComponent,
    BoardUserComponent,
    BoardModeratorComponent,
    HomeComponent,
    LoginComponent,
    ProfileComponent,
    SignupComponent,
    CalendarComponent,
    ProgramComponent,
    RoutinesComponent,
    ExercisesComponent,
    WorkoutComponent,
    AddEditRoutineComponent,
    AddEditExerciseComponent,
    WeeklyProgramComponent,
    FrequencyProgramComponent,
    RoutineVisualizerComponent,
    DialogExerciseSelectComponent,
    RoutineCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FullCalendarModule,
    FormsModule,
    NgbModule,
    NoopAnimationsModule,
    DragDropModule,
    CountdownModule
  ],
  providers: [
    authInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
