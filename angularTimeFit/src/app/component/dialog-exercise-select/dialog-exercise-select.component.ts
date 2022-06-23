import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dialog-exercise-select',
  templateUrl: './dialog-exercise-select.component.html',
  styleUrls: ['./dialog-exercise-select.component.css']
})
export class DialogExerciseSelectComponent implements OnInit {
  selectedExercise: string = ""

  constructor() { }

  ngOnInit(): void {
  }

  select(selectedExercise: string) {
    this.selectedExercise = selectedExercise;
  }

  returnSelected() {

  }
}
