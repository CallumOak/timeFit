import { Component, OnInit } from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {NavbarService} from "../../../service/navbar.service";
import {Routine} from "../../../model/routine.model";
import {Program} from "../../../model/program.model";
import {ProgramService} from "../../../service/program.service";
import {RoutinePlanService} from "../../../service/routine-plan.service";
import {WeeklyRoutinePlan} from "../../../model/weekly-routine-plan.model";
import {ModalDismissReasons, NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";

const ROUTINE_TYPE = RoutineTypeEnum.weekly
const WEEK_DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

@Component({
  selector: 'app-weekly-program',
  templateUrl: './weekly-program.component.html',
  styleUrls: ['./weekly-program.component.css']
})
export class WeeklyProgramComponent implements OnInit {
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

  constructor(private routinePlanService: RoutinePlanService,
              private modalService: NgbModal,
              public routineService: RoutineService,
              private navbarService: NavbarService,
              private programService: ProgramService) {

    this.modalOptions = {
      backdrop: 'static',
      backdropClass: 'customBackdrop'
    }

    this.programService.program$.subscribe(program => {
      this.program = program;

      this.routinePlanService.weeklyRoutinePlanUrls = this.program.weeklyRoutinePlans;
    })

    this.routinePlanService.weeklyRoutinePlans$.subscribe(routinePlans => {
      this.routinePlans = routinePlans;

      this.routineService.routineUrls = this.routineUrls();
    })

    this.routineService.availableRoutines$.subscribe(rs => {
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

  removeRoutine() {
      if(this.routines[this.selectedWeekDay] != null){

        this.routinePlanService.deleteRoutinePlan(this.routinePlans[
          this.routinePlans.findIndex(
            rp => rp.routine.endsWith(this.routines[this.selectedWeekDay].id
            ))
          ].id);
      }
  }

  open(content: any) {
    this.modalService.open(content, this.modalOptions).result.then((result) => {
      this.closeResult = `Closed with: ${result}`
      this.selectedRoutine = this.tmpSelectedRoutine
      //this.routines.push(this.selectedRoutine)
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`
      //this.tmpSelectedRoutine = ;
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
    this.tmpRoutines.forEach((r : Routine) => {
      let index = this.routinePlans.findIndex(rp => rp.routine.endsWith(r.id.toString()))
      if (index > -1){
        let day = this.routinePlans[index].weekDay
        this.routines[day] = r;
      }
    })
  }

}
