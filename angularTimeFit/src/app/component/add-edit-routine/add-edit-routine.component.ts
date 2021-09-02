import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";

const NAV_TITLE = "Routine";
const NAV_PATH = "addEditRoutine";

@Component({
  selector: 'app-add-edit-routine',
  templateUrl: './add-edit-routine.component.html',
  styleUrls: ['./add-edit-routine.component.css']
})
export class AddEditRoutineComponent implements OnInit {

  constructor(private navbarService: NavbarService) {
    this.navbarService.addItem(NAV_TITLE, NAV_PATH)
  }

  ngOnInit(): void {
  }

}
