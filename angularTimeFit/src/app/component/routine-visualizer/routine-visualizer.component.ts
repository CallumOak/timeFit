import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
  private _routine!: Routine;
  @Output()
  updateExercisesEvent = new EventEmitter();
  private _exercises: Exercise[] = [];
  availableRoutines!: Routine[];
  exercisesSubscription: Subscription = new Subscription();
  focusedRoutine?: Routine;
  routineSubscription: Subscription = new Subscription();

  constructor(private modalService: NgbModal,
              private routinePlanService: RoutinePlanService,
              private routineService: RoutineService,
              private exerciseService : ExerciseService) {
  }

  ngOnInit(): void {
    this.exercisesSubscription = this.exerciseService.exercises$.subscribe(es =>{
      let exercises = new Array<Exercise>(this._routine.exercises.length);
      let i = 0;
      this.routine.exercises.forEach(eUrl => {
        let index = es.findIndex(e => eUrl.endsWith("/" + e.id));
        if(index > -1) {
          exercises[this._routine.exercisePositions[i]] = es[index];
        }
        i++;
      })
      if(exercises == []){
        exercises.push(this.exerciseService.emptyExercise);
      }
      this.exercises = exercises;
    })
    this.routineSubscription = this.routineService.selectedRoutine$.subscribe(r =>{
      this.focusedRoutine = r;
    })
  }

  counter(i: number) {
    return new Array(i);
  }

  @Input()
  set routine(value: Routine) {
    this._routine = value;
    this.exerciseService.updateData();
  }

  get routine(): Routine {
    return this._routine;
  }

  get exercises(): Exercise[] {
    return this._exercises;
  }

  set exercises(value: Exercise[]) {
    this._exercises = value;
    this.updateExercisesEvent.emit(value);
  }

  ngOnDestroy(): void {
    this.exercisesSubscription.unsubscribe();
    this.routineSubscription.unsubscribe();
  }

}
