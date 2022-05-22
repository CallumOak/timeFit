import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {ExerciseService} from "../../service/exercise.service";
import {ActivatedRoute} from "@angular/router";
import {Exercise} from "../../model/exercise.model";

const NAV_PATH = "addEditExercise"

@Component({
  selector: 'app-add-edit-exercise',
  templateUrl: './add-edit-exercise.component.html',
  styleUrls: ['./add-edit-exercise.component.css']
})
export class AddEditExerciseComponent implements OnInit {
  selectedExercise: Exercise = new Exercise();
  private navBarItemIndex: number;

  constructor(private navbarService : NavbarService,
              private exerciseService : ExerciseService,
              private activatedRoute: ActivatedRoute) {
    this.navBarItemIndex = this.navbarService.addItem('', '')
    console.log(this.navBarItemIndex);
  }

  ngOnInit(): void {
    this.exerciseService.updateData();
    this.exerciseService.getExerciseById(this.activatedRoute.snapshot.params['id']).subscribe(e => {
      this.selectedExercise = e;
      this.navbarService.editItem(this.navBarItemIndex, this.selectedExercise.name, `${NAV_PATH}/${this.selectedExercise.id}`);
    });
    ;
  }

}
