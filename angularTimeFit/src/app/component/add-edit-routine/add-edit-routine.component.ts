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
  availableExercises: Exercise[] = [];
  exercises: Exercise[] = [];
  tmpSelectedAvailableExercise!: Exercise;
  selectedAvailableExercise!: Exercise;
  selectedExercise: number = -1;

  navBarItemIndex!: number;
  closeResult!: string;
  modalOptions:NgbModalOptions;

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
      this.selectedAvailableExercise = this.tmpSelectedAvailableExercise;
      this.selectedRoutine.exercises.push("/api/exercise/" + this.selectedAvailableExercise.id);
      this.routineService.updateRoutine(this.selectedRoutine);
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`
      this.tmpSelectedAvailableExercise = this.emptyExercise;
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
    this.tmpSelectedAvailableExercise = selectedExercise
  }

  select(selectedExercise: number) {
    this.selectedExercise = selectedExercise
  }

  removeSelectedExercise(){
    this.selectedRoutine.exercises.splice(this.selectedExercise, 1);
    this.routineService.updateRoutine(this.selectedRoutine);
  }

  filterExercises() {
    this.exercises = []
    this.selectedRoutine.exercises.forEach((eUrl : string) => {
      let index = this.availableExercises.findIndex(e => eUrl.endsWith("/" + e.id));
      if(index > -1) {
        this.exercises.push(this.availableExercises[index]);
      }
    });
  }

  selectedExerciseId(): string{
    if(this.selectedExercise > -1 && this.selectedExercise < this.exercises.length){
      return this.exercises[this.selectedExercise].id;
    }
    return "-1";
  }

  ngOnInit(): void {
    this.routineService.updateData();
    this.routineService.routines$.subscribe(rs => {
      this.selectedRoutine = rs[rs.findIndex(r => r.id == this.activatedRoute.snapshot.params['id'])];
      this.navbarService.editItem(this.navBarItemIndex, this.selectedRoutine.id, `${NAV_PATH}/${this.selectedRoutine.name}`)
      this.exerciseService.updateData();
    });
    this.exerciseService.updateData();
    this.exerciseService.getExercises().subscribe(e => {
      this.availableExercises = e;
      this.filterExercises();
    });
  }
}
