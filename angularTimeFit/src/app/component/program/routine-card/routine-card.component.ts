import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-routine-card',
  templateUrl: './routine-card.component.html',
  styleUrls: ['./routine-card.component.css']
})
export class RoutineCardComponent implements OnInit {

  @Input()
  image!: string;

  @Input()
  title: string = "...";

  @Input()
  present: boolean = true;

  @Input()
  id!: string;

  @Output()
  add = new EventEmitter<string>();

  @Output()
  remove = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

}
