import {Component, OnInit} from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {Routine} from "../../../model/routine.model";
import {RoutinePlanService} from "../../../service/routine-plan.service";
import {FrequencyRoutinePlan} from "../../../model/frequency-routine-plan.model";
import {Program} from "../../../model/program.model";
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {NavbarService} from "../../../service/navbar.service";
import {ProgramService} from "../../../service/program.service";
import {WeeklyRoutinePlan} from "../../../model/weekly-routine-plan.model";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {Subscription} from "rxjs";

const ROUTINE_TYPE = RoutineTypeEnum.frequency

@Component({
  selector: 'app-frequency-program',
  templateUrl: './frequency-program.component.html',
  styleUrls: ['./frequency-program.component.css']
})
export class FrequencyProgramComponent implements OnInit {
  program!: Program;
  routineType = ROUTINE_TYPE;
  routinePlans!: FrequencyRoutinePlan[];
  emptyRoutine = new Routine();
  tmpRoutines : Routine[] = [];
  modalOptions: NgbModalOptions;
  tmpSelectedRoutine!: Routine;
  selectedRoutine!: Routine;
  closeResult!: string;
  routines: Routine[] = [];
  private _selectedIndex: number = 0;
  programSubscription!: Subscription;
  routinePlanSubscription!: Subscription;
  routineSubscription!: Subscription;

  constructor(public routinePlanService: RoutinePlanService,
              private modalService: NgbModal,
              public routineService: RoutineService,
              private navbarService: NavbarService,
              private programService: ProgramService) {

    this.modalOptions = {
      backdrop: 'static',
      backdropClass: 'customBackdrop'
    }

    this.programSubscription = this.programService.program$.subscribe(program => {
      this.program = program;

      this.routinePlanService.weeklyRoutinePlanUrls = this.program.weeklyRoutinePlans;
    })

    this.routinePlanSubscription = this.routinePlanService.frequencyRoutinePlans$.subscribe(routinePlans => {
      this.routinePlans = routinePlans;

      this.routineService.routineUrls = this.routineUrls();
    })

    this.routineSubscription = this.routineService.availableRoutines$.subscribe(rs => {
      this.tmpRoutines = rs;
      this.orderRoutines();
    })
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.routines, event.previousIndex, event.currentIndex);

    let routinePlan = this.routinePlans[this.routinePlans.findIndex(rp => rp.position == event.previousIndex)];
    routinePlan.position = event.currentIndex;
    this.routinePlanService.updateFrequencyRoutinePlan(routinePlan);
  }

  get selectedIndex(): number {
    return this._selectedIndex;
  }

  set selectedIndex(value: number) {
    this._selectedIndex = value;
    this.routinePlanService.selectedIndex = value;
  }

  ngOnInit(): void {
  }

  canAddRoutine(i: number){
    return i < this.routines.length && this.routines[i] == null && (i == 0 || this.routines[i - 1] != null);
  }

  removeRoutine(id: string) {
    let routinePlanIndex = this.routinePlans.findIndex(rp => rp.routine.endsWith("/" + id));
    this.routinePlanService.deleteRoutinePlan(this.routinePlans[routinePlanIndex].id);
  }

  updateStartDate(event: any){
    this.program.startDate = event.target.value;
    this.updateProgram();
  }

  updateFrequency(event: any){
    this.program.frequency = event.target.value;
    this.updateProgram();
  }

  updateProgram(){
    this.programService.updateProgram(this.program);
  }

  routineUrls(): string[]{
    let urls: string[] = [];
    this.routinePlans.forEach(rp => urls.push(rp.routine));
    return urls;
  }

  orderRoutines(){
    this.routines = new Array<Routine>(this.routinePlans.length)
    this.routinePlans.forEach((rp : FrequencyRoutinePlan) => {
      let id = rp.routine.split("/")[rp.routine.split("/").length - 1]
      let index = this.tmpRoutines.findIndex(r => r.id == id)
      if (index > -1){
        this.routines[rp.position] = this.tmpRoutines[index];
      }
    })
  }

  open(content: any) {
    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`
      if(this.selectedRoutine != this.emptyRoutine){
        this.selectedRoutine = this.tmpSelectedRoutine
        let routinePlan = new FrequencyRoutinePlan();
        routinePlan.routine = '/api/routine/' + this.selectedRoutine.id;
        routinePlan.program = '/api/program/' + this.program.id;
        routinePlan.position = this.routinePlans.length;
        this.routinePlanService.createFrequencyRoutinePlan(routinePlan);
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`
      this.tmpSelectedRoutine = this.emptyRoutine;
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

  tmpSelect(selectedRoutine: Routine) {
    this.tmpSelectedRoutine = selectedRoutine
  }

  select(selectedRoutine: Routine) {
    this.selectedRoutine = selectedRoutine
  }

  ngOnDestroy(): void {
    this.programSubscription.unsubscribe();
    this.routinePlanSubscription.unsubscribe();
    this.routineSubscription.unsubscribe();
  }
}
