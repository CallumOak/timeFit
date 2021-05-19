import {Component, OnInit} from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { CalendarOptions } from '@fullcalendar/angular';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit{


  Events = [];
  // @ts-ignore

  constructor(private httpClient: HttpClient){ }

  // @ts-ignore
  onDateClick(res) {
    alert('Clicked on date : ' + res.dateStr)
  }

  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    dateClick: this.onDateClick.bind(this), // bind is important!
    events: [
      { title: 'event 1', date: '2021-05-27' },
      { title: 'event 2', date: '2021-05-30' }
    ]
  };

  ngOnInit(){
    setTimeout(() => {
      return this.httpClient.get('http://localhost:8888/event.php')
        .subscribe(res => {
          // @ts-ignore
          this.Events.push(res);
          console.log(this.Events);
        });
    }, 2200);

    setTimeout(() => {
      this.calendarOptions = {
        initialView: 'dayGridMonth',
        dateClick: this.onDateClick.bind(this),
        events: this.Events
      };
    }, 2500);

  }

  removerSelectedRoutine() {

  }
}
