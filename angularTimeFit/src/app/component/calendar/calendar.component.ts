import { Component } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { CalendarOptions } from '@fullcalendar/angular';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent {


  Events = [];
  // @ts-ignore
  calendarOptions: CalendarOptions;

  constructor(private httpClient: HttpClient) { }

  // @ts-ignore
  onDateClick(res) {
    alert('Clicked on date : ' + res.dateStr)
  }

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

}
