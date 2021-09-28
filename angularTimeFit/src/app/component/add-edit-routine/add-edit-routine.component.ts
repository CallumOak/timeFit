import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute, ParamMap} from '@angular/router';
import { RoutineService } from 'src/app/service/routine.service';
import {switchMap} from "rxjs/operators";

const NAV_PATH = "addEditRoutine";

@Component({
  selector: 'app-add-edit-routine',
  templateUrl: './add-edit-routine.component.html',
  styleUrls: ['./add-edit-routine.component.css']
})
export class AddEditRoutineComponent implements OnInit {
  exerciseOptions: string[] = ['Biceps', 'Triceps', 'Pushups', 'Shoulder press', 'Quadriceps', 'Abs']
  exercises: string[] = ['Biceps', 'Triceps', 'Pushups'];
  closeResult!: string;
  modalOptions:NgbModalOptions;
  tmpSelectedExercise: string = '';
  selectedExercise: string = '';
  selectedRoutine: string = '';
  selectedRoutineId: string = '';
  navBarItemIndex!: number;

  constructor(private navbarService: NavbarService,
              private modalService: NgbModal,
              private activatedRoute: ActivatedRoute,
              private routineService: RoutineService) {
    this.modalOptions = {
      backdrop:'static',
      backdropClass:'customBackdrop'
    }
    this.navBarItemIndex = this.navbarService.addItem('', '')
  }

  open(content: any) {
    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`
      this.selectedExercise = this.tmpSelectedExercise
      this.exercises.push(this.selectedExercise)
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`
      this.tmpSelectedExercise = ''
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  tmpSelect(selectedExercise: string) {
    this.tmpSelectedExercise = selectedExercise
  }

  select(selectedExercise: string) {
    this.selectedExercise = selectedExercise
  }

  removeSelectedExercise(){
    for(let i = this.exercises.length - 1; i >= 0; i--){
        if(this.exercises[i]==this.selectedExercise)
          this.exercises.splice(i,1)
    }
    this.selectedExercise = ''
  }

  ngOnInit(): void {
    this.selectedRoutineId = this.activatedRoute.snapshot.params['id'];
    console.log(`Params : ${this.selectedRoutineId}`);
    this.routineService.getRoutineBasedOnType(+this.selectedRoutineId).subscribe(routine => {
      this.selectedRoutine = routine;
    });
    this.routineService.updateData();
    this.navbarService.editItem(this.navBarItemIndex, this.selectedRoutine, `${NAV_PATH}/${this.selectedRoutineId}`)
  }
}
