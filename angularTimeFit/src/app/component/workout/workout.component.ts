import {Component, OnDestroy, OnInit} from '@angular/core';
import {Exercise} from "../../model/exercise.model";
import {Subscription} from "rxjs";
import { ExerciseService } from 'src/app/service/exercise.service';

@Component({
  selector: 'app-workout',
  templateUrl: './workout.component.html',
  styleUrls: ['./workout.component.css']
})
export class WorkoutComponent implements OnInit, OnDestroy {
  available: Exercise[] = [];
  selected!: Exercise;
  subscription!: Subscription;

  constructor(private exerciseService: ExerciseService) { }

  ngOnInit(): void {
    this.subscription = this.exerciseService.exercises$.subscribe(es => this.available = es);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
