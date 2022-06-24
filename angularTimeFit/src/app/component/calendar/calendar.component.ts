import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';

import {HttpClient} from '@angular/common/http';
import {RoutineService} from "../../service/routine.service";
import {RoutinePlanService} from "../../service/routine-plan.service";
import {RoutinePlan} from "../../model/routine-plan.model";
import {Routine} from "../../model/routine.model";
import {Subscription} from "rxjs";
import {Program} from "../../model/program.model";
import {ProgramService} from "../../service/program.service";
import {WeeklyRoutinePlan} from "../../model/weekly-routine-plan.model";
import {FrequencyRoutinePlan} from 'src/app/model/frequency-routine-plan.model';
import {CalendarOptions} from "@fullcalendar/core";
import {FullCalendarComponent} from "@fullcalendar/angular";
import {Router} from "@angular/router";


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit, OnDestroy{
  @ViewChild('calendar')
  calendarComponent!: FullCalendarComponent;
  program!: Program;
  weeklyRoutinePlans: WeeklyRoutinePlan[] = [];
  frequencyRoutinePlans: FrequencyRoutinePlan[] = [];
  routines: Routine[] = [];
  programSubscription: Subscription = new Subscription();
  weeklyRoutinePlanSubscription: Subscription = new Subscription();
  frequencyRoutinePlanSubscription: Subscription = new Subscription();
  routineSubscription: Subscription = new Subscription();
  selectedRoutinePlan!: RoutinePlan;

  Events : any[] = [];

  calendarOptions : CalendarOptions = {
    initialView: 'dayGridMonth',
    events: this.Events,

  };

  constructor(private httpClient: HttpClient,
              private router: Router,
              private programService: ProgramService,
              private routinePlanService: RoutinePlanService,
              private routineService: RoutineService){ }

  fillEvents(){
    this.Events = [];
    if(this.program.programSetting == "weekly"){
      for(let i = 0; i < this.weeklyRoutinePlans.length; i++){
        let routine = this.routine(this.weeklyRoutinePlans[i].routine);
        let day: number = this.weeklyRoutinePlans[i].weekDay.valueOf();
        let event = {
          title: routine.name,
          rrule: {
            freq: 'weekly',
            interval: 1,
            byweekday: [day],
            dtstart: new Date().getFullYear() + '-01-01'
          },
          backgroundColor: routine.color,
          url: "http://localhost:4200/workout/" + routine.id
        }
        this.Events.push(event);
      }
    }

    if(this.program.programSetting == "frequency"){
      for(let i = 0; i < this.frequencyRoutinePlans.length; i++){
        let routine = this.routine(this.frequencyRoutinePlans[i].routine);
        let date: Date = this.addDays(this.program.frequency * i ,new Date(this.program.startDate));
        let event = {
          title: routine.name,
          rrule: {
            freq: "daily",
            interval: this.program.frequency * this.program.frequencyRoutinePlans.length,
            dtstart: date
          },
          backgroundColor: routine.color,
          url: "http://localhost:4200/workout/" + routine.id
        }
        this.Events.push(event);
      }
    }
    this.calendarOptions.events = this.Events;
  }

  addDays(days : number, date: Date): Date{
    date.setDate(date.getDate() + days);
    return date;
  }


  routineUrls(): string[]{
    let urls: string[] = [];
    let routinePlans : RoutinePlan[] = this.program.programSetting == "weekly" ? this.weeklyRoutinePlans : this.frequencyRoutinePlans;
    routinePlans.forEach(rp => urls.push(rp.routine));
    return urls;
  }

  routine(routineUrl: string): Routine{
    let routineUrlParts: string[] = routineUrl.split("/");
    let routineId = routineUrlParts[routineUrlParts.length - 1];
    return this.routines[this.routines.findIndex(r=>r.id==routineId)];
  }

  ngOnInit(){
    this.programSubscription = this.programService.program$.subscribe(program => {
      this.program = program;
      this.routinePlanService.weeklyRoutinePlanUrls = this.program.weeklyRoutinePlans;
      this.routinePlanService.frequencyRoutinePlanUrls = this.program.frequencyRoutinePlans;
      if(program.programSetting == "weekly"){
        this.weeklyRoutinePlanSubscription = this.routinePlanService.weeklyRoutinePlans$.subscribe(routinePlans => {
          this.weeklyRoutinePlans = routinePlans;
          this.routineService.routineUrls = this.routineUrls();
          this.routineSubscription = this.routineService.availableRoutines$.subscribe(rs => {
            this.routines = rs;
            this.fillEvents();
          })
        })
      }

      if(program.programSetting == "frequency") {
        this.frequencyRoutinePlanSubscription = this.routinePlanService.frequencyRoutinePlans$.subscribe(routinePlans => {
          this.frequencyRoutinePlans = routinePlans;
          this.routineService.routineUrls = this.routineUrls();
          this.routineSubscription = this.routineService.availableRoutines$.subscribe(rs => {
            this.routines = rs;
            this.fillEvents();
          })
        })
      }
    })
  }

  ngOnDestroy(): void {
    this.programSubscription.unsubscribe();
    this.weeklyRoutinePlanSubscription.unsubscribe();
    this.frequencyRoutinePlanSubscription.unsubscribe();
    this.routineSubscription.unsubscribe();
  }

  todayEvent() {
    let calendarApi = this.calendarComponent.getApi();
    calendarApi.getCurrentData();
  }
}
