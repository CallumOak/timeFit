import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {UserService} from "./service/user.service";

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

import { FullCalendarModule } from '@fullcalendar/angular';
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';
import { CalendarComponent } from './component/calendar/calendar.component';

FullCalendarModule.registerPlugins([
  interactionPlugin,
  dayGridPlugin
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
    CalendarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FullCalendarModule,
    FormsModule
  ],
  providers: [UserService, authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
