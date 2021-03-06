import {Component, OnDestroy, OnInit} from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {NavbarService} from "../../../service/navbar.service";
import {Routine} from "../../../model/routine.model";
import {Program} from "../../../model/program.model";
import {ProgramService} from "../../../service/program.service";
import {RoutinePlanService} from "../../../service/routine-plan.service";
import {WeeklyRoutinePlan} from "../../../model/weekly-routine-plan.model";
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {Subscription} from "rxjs";

const ROUTINE_TYPE = RoutineTypeEnum.weekly
const WEEK_DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

@Component({
  selector: 'app-weekly-program',
  templateUrl: './weekly-program.component.html',
  styleUrls: ['./weekly-program.component.css']
})
export class WeeklyProgramComponent implements OnInit, OnDestroy {
  program!: Program;
  routineType = ROUTINE_TYPE;
  routinePlans!: WeeklyRoutinePlan[];
  emptyRoutine = new Routine();
  tmpRoutines : Routine[] = [];
  modalOptions: NgbModalOptions;
  tmpSelectedRoutine!: Routine;
  selectedRoutine!: Routine;
  closeResult!: string;
  routines: (Routine)[] = [this.emptyRoutine, this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine];
  //subscription!: Subscription;
  weekDays: string[] = WEEK_DAYS;
  private _selectedWeekDay = 0;
  programSubscription!: Subscription;
  routinePlanSubscription!: Subscription;
  routineSubscription!: Subscription;

  constructor(private routinePlanService: RoutinePlanService,
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

    this.routinePlanSubscription = this.routinePlanService.weeklyRoutinePlans$.subscribe(routinePlans => {
      this.routinePlans = routinePlans;

      this.routineService.routineUrls = this.routineUrls();
    })

    this.routineSubscription = this.routineService.availableRoutines$.subscribe(rs => {
      this.tmpRoutines = rs;
      this.routines = [this.emptyRoutine, this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine];
      this.orderRoutines();
    })

  }

  get selectedWeekDay(): number {
    return this._selectedWeekDay;
  }

  set selectedWeekDay(value: number) {
    this._selectedWeekDay = value;
    this.routinePlanService.selectedDay = value;
  }

  ngOnInit(): void {
  }


  routineUrls(): string[]{
      let urls: string[] = [];
      this.routinePlans.forEach(rp => urls.push(rp.routine));
      return urls;
  }

  removeRoutine(i: number) {
      this.routinePlanService.deleteRoutinePlan(this.routinePlans[
        this.routinePlans.findIndex(
          rp => rp.weekDay == i)
        ].id);
  }

  open(content: any) {
    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`
      this.selectedRoutine = this.tmpSelectedRoutine
      let routinePlan = new WeeklyRoutinePlan();
      routinePlan.routine = '/api/routine/' + this.selectedRoutine.id;
      routinePlan.program = '/api/program/' + this.program.id;
      routinePlan.weekDay = this.selectedWeekDay;
      this.routinePlanService.createWeeklyRoutinePlan(routinePlan)
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

  orderRoutines(){
    this.routinePlans.forEach((rp : WeeklyRoutinePlan) => {
      let id = rp.routine.split("/")[rp.routine.split("/").length - 1]
      let index = this.tmpRoutines.findIndex(r => r.id == id)
      if (index > -1){
        this.routines[rp.weekDay] = this.tmpRoutines[index];
      }
    })
  }

  ngOnDestroy(): void {
    this.programSubscription.unsubscribe();
    this.routinePlanSubscription.unsubscribe();
    this.routineSubscription.unsubscribe();
  }

}
