import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";

const NAV_TITLE = "Exercise"
const NAV_PATH = "addEditExercise"

@Component({
  selector: 'app-add-edit-exercise',
  templateUrl: './add-edit-exercise.component.html',
  styleUrls: ['./add-edit-exercise.component.css']
})
export class AddEditExerciseComponent implements OnInit {

  constructor(private navBarService : NavbarService) {
    this.navBarService.addItem(NAV_TITLE, NAV_PATH)
  }

  ngOnInit(): void {
  }

}
