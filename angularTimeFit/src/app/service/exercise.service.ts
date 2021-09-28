import { Injectable } from '@angular/core';
import {Exercise} from "../model/exercise.model";

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor() { }

  getExercise(selectedExerciseId: string): Exercise {
    return new Exercise(selectedExerciseId,
      selectedExerciseId,
      '00:30',
      '00:10',
      '00:06',
      "#acolor",
      "#bcoler",
      "http://somewhere"
    );
  }
}
