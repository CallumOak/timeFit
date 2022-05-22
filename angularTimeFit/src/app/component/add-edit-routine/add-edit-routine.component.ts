import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute, ParamMap} from '@angular/router';
import { RoutineService } from 'src/app/service/routine.service';
import {switchMap} from "rxjs/operators";
import { RoutinePlan } from 'src/app/model/routine-plan.model';
import {Routine} from "../../model/routine.model";
import {Exercise} from "../../model/exercise.model";
import {ExerciseService} from "../../service/exercise.service";

const NAV_PATH = "addEditRoutine";

@Component({
  selector: 'app-add-edit-routine',
  templateUrl: './add-edit-routine.component.html',
  styleUrls: ['./add-edit-routine.component.css']
})
export class AddEditRoutineComponent implements OnInit {
  emptyExercise = new Exercise();
  selectedRoutine: Routine = new Routine();
  //exerciseOptions: string[] = ['Biceps', 'Triceps', 'Pushups', 'Shoulder press', 'Quadriceps', 'Abs']
  exercises: Exercise[] = [];
  availableExercises: Exercise[] = [];
  closeResult!: string;
  modalOptions:NgbModalOptions;
  tmpSelectedExercise!: Exercise;
  selectedExercise!: Exercise;
  selectedRoutineId: string = '';
  navBarItemIndex!: number;

  constructor(private navbarService: NavbarService,
              private modalService: NgbModal,
              private activatedRoute: ActivatedRoute,
              private exerciseService: ExerciseService,
              private routineService: RoutineService) {
    this.navBarItemIndex = this.navbarService.addItem('', '')
    console.log(this.navBarItemIndex);
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
      this.tmpSelectedExercise = this.emptyExercise;
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

  tmpSelect(selectedExercise: Exercise) {
    this.tmpSelectedExercise = selectedExercise
  }

  select(selectedExercise: Exercise) {
    this.selectedExercise = selectedExercise
  }

  removeSelectedExercise(){
    for(let i = this.exercises.length - 1; i >= 0; i--){
      if(this.exercises[i]==this.selectedExercise)
        this.exercises.splice(i,1)
    }
    this.selectedExercise = this.emptyExercise;
  }

  filterExercises() {
    this.availableExercises.forEach((e : Exercise) => {
      let index = this.selectedRoutine.exercises.findIndex(sre => sre.endsWith(e.id.toString()))
      if(index > -1) {
        this.exercises.push(e);
      }
    });
  }

  ngOnInit(): void {
    /*this.selectedRoutineId = this.activatedRoute.snapshot.params['id'];
    console.log(`Params : ${this.selectedRoutineId}`);
    this.routineService.getRoutineById(this.selectedRoutineId).subscribe(routine => {
      this.selectedRoutine = routine;
    });
    this.routineService.updateData();
    this.navbarService.editItem(this.navBarItemIndex, this.selectedRoutine.id, `${NAV_PATH}/${this.selectedRoutineId}`)*/
    this.routineService.updateData();
    this.routineService.getRoutineById(this.activatedRoute.snapshot.params['id']).subscribe(e => {
      this.selectedRoutine = e;
      this.navbarService.editItem(this.navBarItemIndex, this.selectedRoutine.id, `${NAV_PATH}/${this.selectedRoutineId}`)
    });
    this.exerciseService.updateData();
    this.exerciseService.getExercises().subscribe(e => {
      this.availableExercises = e;
      this.filterExercises();
    });

  }
}
