import {Component, Input, OnInit} from '@angular/core';
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {RoutineService} from "../../service/routine.service";
import {RoutineTypeEnum} from "../../enums/routine-type-enum.enum";
import {Subscription} from "rxjs";
import {Routine} from "../../model/routine.model";
import {RoutinePlanService} from "../../service/routine-plan.service";
import {Exercise} from "../../model/exercise.model";
import {ExerciseService} from "../../service/exercise.service";

@Component({
  selector: 'app-routine-visualizer',
  templateUrl: './routine-visualizer.component.html',
  styleUrls: ['./routine-visualizer.component.css']
})

export class RoutineVisualizerComponent implements OnInit {
  @Input()
  routineType!: RoutineTypeEnum;
  @Input()
  routine!: Routine;
  exercises: Exercise[] = [];
  closeResult: string = '';
  availableRoutines!: Routine[];
  tmpSelectedRoutine!: Routine;
  selectedRoutine!: Routine;
  private modalOptions: NgbModalOptions;
  selectedRoutineSubscription!: Subscription;
  availableRoutinesSubscription!: Subscription;

  constructor(private modalService: NgbModal,
              private routinePlanService: RoutinePlanService,
              private routineService: RoutineService,
              private exerciseService : ExerciseService) {
    this.modalOptions = {
      backdrop:'static',
      backdropClass:'customBackdrop'
    }
  }
/*
  private commitSelectedRoutine(){
    this.selectedRoutine = this.tmpSelectedRoutine

    this.routinePlanService.setRoutine(this.routineType, this.selectedRoutine)
  }

  open(content: any) {
    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`
      this.commitSelectedRoutine()
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`
    });
    this.tmpSelectedRoutine = this.availableRoutines[0]
    console.log(this.closeResult)
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
*/
  ngOnInit(): void {
    //console.log(`Routine type : ${(this.routineType)}`)
    this.availableRoutinesSubscription = this.routineService.availableRoutines$.subscribe(routines => {
      this.availableRoutines = routines;
      this.exerciseService.updateData();
    })
    this.exerciseService.exercises$.subscribe(es =>{
        this.exercises = [];
        this.routine.exercises.forEach(e => {
          let id = e.split("/")[e.split("/").length - 1];
          let index = es.findIndex(er => er.id == id)
          if(index > -1){
            this.exercises.push(es[index])
          }
        })
    }
    )
    this.tmpSelectedRoutine = this.availableRoutines[0]
    console.log(`Selected routine : ${this.selectedRoutine}`)
  }

  tmpSelect(routine: Routine) {
    this.tmpSelectedRoutine = routine
  }

  counter(i: number) {
    return new Array(i);
  }

  ngOnDestroy(): void {
    this.selectedRoutineSubscription.unsubscribe();
    this.availableRoutinesSubscription.unsubscribe();
  }

}
