import { Component, OnInit, OnDestroy } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute, ParamMap} from '@angular/router';
import { RoutineService } from 'src/app/service/routine.service';
import {switchMap} from "rxjs/operators";
import { RoutinePlan } from 'src/app/model/routine-plan.model';
import {Routine} from "../../model/routine.model";
import {Exercise} from "../../model/exercise.model";
import {ExerciseService} from "../../service/exercise.service";
import {Subscription} from "rxjs";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";

const NAV_PATH = "addEditRoutine";
const EXERCISE_PATH = "/api/exercise/";

@Component({
  selector: 'app-add-edit-routine',
  templateUrl: './add-edit-routine.component.html',
  styleUrls: ['./add-edit-routine.component.css']
})
export class AddEditRoutineComponent implements OnInit, OnDestroy {
  emptyExercise = new Exercise();
  selectedRoutine: Routine = new Routine();
  availableExercises: Exercise[] = [];
  exercises: Exercise[] = [];
  tmpSelectedAvailableExercise!: Exercise;
  selectedAvailableExercise!: Exercise;
  selectedExercise: number = -1;
  routineSubscription!: Subscription;
  exerciseSubscription!: Subscription;
  navBarItemIndex!: number;
  closeResult!: string;
  modalOptions:NgbModalOptions;

  constructor(private navbarService: NavbarService,
              private routineService: RoutineService,
              private modalService: NgbModal,
              private activatedRoute: ActivatedRoute,
              private exerciseService: ExerciseService) {

    this.navBarItemIndex = this.navbarService.addItem('', '')
    console.log(this.navBarItemIndex);

    this.modalOptions = {
      backdrop:'static',
      backdropClass:'customBackdrop'
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.exercises, event.previousIndex, event.currentIndex);

    let movingValueIndex = this.selectedRoutine.exercisePositions.indexOf(event.previousIndex);

    let low = Math.min(event.previousIndex, event.currentIndex);
    let high = Math.max(event.previousIndex, event.currentIndex);
    let increment = event.previousIndex > event.currentIndex ? 1 : -1;

    for (let i = 0; i < this.selectedRoutine.exercisePositions.length; i++){
      let p = this.selectedRoutine.exercisePositions[i];
      if(p >= low && p <= high){
        this.selectedRoutine.exercisePositions[i] = p + increment;
      }
    }

    this.selectedRoutine.exercisePositions[movingValueIndex] = event.currentIndex;

    this.routineService.updateRoutine(this.selectedRoutine);
  }

  open(content: any) {
    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`
      this.selectedAvailableExercise = this.tmpSelectedAvailableExercise;
      this.selectedRoutine.exercisePositions.push(this.selectedRoutine.exercisePositions.length);
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

    this.exercises = new Array<Exercise>(this.selectedRoutine.exercises.length);
    let i = 0;
    this.selectedRoutine.exercises.forEach((eUrl : string) => {
      let index = this.availableExercises.findIndex(e => eUrl.endsWith("/" + e.id));
      if(index > -1) {
        this.exercises[this.selectedRoutine.exercisePositions[i]] = this.availableExercises[index];
      }
      i++;
    });
  }

  selectedExerciseId(): string{
    if(this.selectedExercise > -1 && this.selectedExercise < this.exercises.length){
      return this.exercises[this.selectedExercise].id;
    }
    return "-1";
  }

  ngOnInit(): void {
    this.routineSubscription = this.routineService.availableRoutines$.subscribe(rs =>
      this.getRoutine());
      this.exerciseSubscription = this.exerciseService.exercises$.subscribe(e => {
        this.availableExercises = e;
        this.filterExercises();
    });
  }

  ngOnDestroy() {
    this.routineSubscription.unsubscribe();
    this.exerciseSubscription.unsubscribe();
    console.log("destroyed")
  }

  getRoutine(){
    this.routineService.getRoutineById(this.activatedRoute.snapshot.params['id']).subscribe(r => {
      this.selectedRoutine = r;
      this.navbarService.editItem(this.navBarItemIndex, this.selectedRoutine.name, `${NAV_PATH}/${this.selectedRoutine.id}`);
      this.exerciseService.updateData();
    });
  }

  updateRoutineColor(event: any) {
    this.selectedRoutine.color = event.target.value;
    this.routineService.updateRoutine(this.selectedRoutine);
  }

  updateRoutineName(event: any) {
    this.selectedRoutine.name = event.target.value;
    this.routineService.updateRoutine(this.selectedRoutine);
  }

  updateIllustration(event: any) {
    this.selectedRoutine.illustrationLocation = event.target.value;
    this.routineService.updateRoutine(this.selectedRoutine);
  }
}
