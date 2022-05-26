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
  selectedExercise: Exercise = new Exercise();
  private navBarItemIndex: number;
  exerciseSubscription!: Subscription;

  constructor(private navbarService : NavbarService,
              private exerciseService : ExerciseService,
              private activatedRoute: ActivatedRoute) {

    this.navBarItemIndex = this.navbarService.addItem('', '')
    console.log(this.navBarItemIndex);
  }

  ngOnInit(): void {
    this.exerciseService.updateData();
    this.onSubmit();
  }

  onSubmit() {
    this.exerciseSubscription = this.exerciseService.getExerciseById(this.activatedRoute.snapshot.params['id']).subscribe(e => {
      this.selectedExercise = e;
      this.navbarService.editItem(this.navBarItemIndex, this.selectedExercise.name, `${NAV_PATH}/${this.selectedExercise.id}`);
    });
  }

  ngOnDestroy(): void {
    this.exerciseSubscription.unsubscribe();
  }
}
