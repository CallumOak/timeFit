import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {RoutineService} from "../../service/routine.service";
import {RoutineTypeEnum} from "../../enums/routine-type-enum.enum";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-routine-visualizer',
  templateUrl: './routine-visualizer.component.html',
  styleUrls: ['./routine-visualizer.component.css']
})

export class RoutineVisualizerComponent implements OnInit {
  @Input()
  routineType!: RoutineTypeEnum;
  @Input()
  routineService!: RoutineService
  closeResult: string = '';
  availableRoutines!: string[];
  tmpSelectedRoutine: string = '';
  selectedRoutine: string= 'Routine';
  private modalOptions: NgbModalOptions;
  selectedRoutineSubscription!: Subscription;
  availableRoutinesSubscription!: Subscription;

  constructor(private modalService: NgbModal) {
    this.modalOptions = {
      backdrop:'static',
      backdropClass:'customBackdrop'
    }
  }

  private commitSelectedRoutine(){
    this.selectedRoutine = this.tmpSelectedRoutine
    this.routineService.setRoutineBasedOnType(this.routineType, this.selectedRoutine)
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

  ngOnInit(): void {
    console.log(`Routine type : ${(this.routineType)}`)
    this.selectedRoutineSubscription = this.routineService.getRoutineBasedOnType(this.routineType).subscribe(routine => {
      this.selectedRoutine = routine
    })
    this.availableRoutinesSubscription = this.routineService.availableRoutines$.subscribe(routines => {
      this.availableRoutines = routines;
    })
    this.routineService.updateData()
    this.tmpSelectedRoutine = this.availableRoutines[0]
    console.log(`Selected routine : ${this.selectedRoutine}`)
  }

  tmpSelect(routine: string) {
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
