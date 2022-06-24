import {Component, OnDestroy, OnInit} from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {ExerciseService} from "../../service/exercise.service";
import {ActivatedRoute} from "@angular/router";
import {Exercise} from "../../model/exercise.model";
import {Subscription} from "rxjs";

const NAV_PATH = "addEditExercise"

@Component({
  selector: 'app-add-edit-exercise',
  templateUrl: './add-edit-exercise.component.html',
  styleUrls: ['./add-edit-exercise.component.css']
})
export class AddEditExerciseComponent implements OnInit, OnDestroy {
  selectedExercise!: Exercise;
  private navBarItemIndex: number;
  exerciseSubscription!: Subscription;

  constructor(private navbarService : NavbarService,
              private exerciseService : ExerciseService,
              private activatedRoute: ActivatedRoute) {
    this.selectedExercise = this.exerciseService.emptyExercise;
    this.navBarItemIndex = this.navbarService.addItem('', '')
    console.log(this.navBarItemIndex);
  }

  ngOnInit(): void {
    this.exerciseSubscription = this.exerciseService.exercises$.subscribe(es => {
      this.exerciseService.getExerciseById(this.activatedRoute.snapshot.params['id']).subscribe(e =>{
          this.selectedExercise = e;
          this.navbarService.editItem(this.navBarItemIndex, e.name, `${NAV_PATH}/${e.id}`);
        })
    });
  }

  updateName(event: any){
    this.selectedExercise.name = event.target.value != undefined ? event.target.value : "";
    this.update()
  }

  updateTime(event: any){
    this.selectedExercise.exerciseDuration = +event.target.value != undefined ? +event.target.value : 0;
    this.update()
  }

  updateReps(event: any){
    this.selectedExercise.repetitions = event.target.value != undefined ? event.target.value : 0;
    this.update()
  }

  updateRest(event: any){
    this.selectedExercise.exerciseBreak = +event.target.value != undefined ? +event.target.value : 0;
    this.update()
  }

  updateExerciseColor(event: any){
    this.selectedExercise.exerciseColor = event.target.value != undefined ? event.target.value : "";
    this.update()
  }

  updateRestColor(event: any){
    this.selectedExercise.breakColor = event.target.value != undefined ? event.target.value : "";
    this.update()
  }

  updateIllustration(event: any){
    this.selectedExercise.illustrationLocation = event.target.value != undefined && event.target.value.length < 1001 ? event.target.value : "";
    this.update()
  }

  update(){
    this.exerciseService.updateExercise(this.selectedExercise);
  }

  ngOnDestroy(): void {
    this.exerciseSubscription.unsubscribe();
  }
}
