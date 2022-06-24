import {Component, OnDestroy, OnInit} from '@angular/core';
import {Exercise} from "../../model/exercise.model";
import {Subscription} from "rxjs";
import {NavbarService} from "../../service/navbar.service";
import {Router} from "@angular/router";
import {ExerciseService} from "../../service/exercise.service";

@Component({
  selector: 'app-exercises',
  templateUrl: './exercises.component.html',
  styleUrls: ['./exercises.component.css']
})
export class ExercisesComponent implements OnInit, OnDestroy {
  exercises!: Exercise[];
  selectedExercise?: Exercise;
  private exerciseSubscription: Subscription = new Subscription();

  constructor(
    private exerciseService : ExerciseService,
    private navbarService: NavbarService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.exerciseService.exercises$.subscribe(es =>
      this.exercises = es
    );
    this.exerciseSubscription = this.exerciseService.selectedExercise$.subscribe(e =>{
      this.selectedExercise = e;
    });
    this.exerciseService.updateData();
  }

  ngOnDestroy() {
    this.exerciseSubscription.unsubscribe();
  }

  select(i: number) {
    this.exerciseService.selectExercise(this.exercises[i].id);
  }

  addExercise() {
    this.exerciseService.createExercise().subscribe(e => {
      this.exerciseService.updateData();
      this.router.navigate(["/addEditExercise/" + e.id]);
    });
  }

  deleteExercise() {
    if(this.selectedExercise != undefined){
      this.exerciseService.deleteExercise(this.selectedExercise);
      if(this.navbarService.getByName(this.selectedExercise.name)){
        if( this.router.url.includes((this.navbarService.getByName(this.selectedExercise.name)).path)){
          this.router.navigate(["/calendar"])
        }
        this.navbarService.removeItem(this.selectedExercise.name);
      }
    }
  }
}
