import {Component, OnInit} from '@angular/core';
import {RoutineTypeEnum} from "../../../enums/routine-type-enum.enum";
import {RoutineService} from "../../../service/routine.service";
import {Routine} from "../../../model/routine.model";
import {RoutinePlanService} from "../../../service/routine-plan.service";
import {FrequencyRoutinePlan} from "../../../model/frequency-routine-plan.model";
import {Program} from "../../../model/program.model";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {NavbarService} from "../../../service/navbar.service";
import {ProgramService} from "../../../service/program.service";

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

  constructor(public routinePlanService: RoutinePlanService,
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

    this.routinePlanService.frequencyRoutinePlans$.subscribe(routinePlans => {
      this.routinePlans = routinePlans;

      this.routineService.routineUrls = this.routineUrls();
    })

    this.routineService.availableRoutines$.subscribe(rs => {
      this.tmpRoutines = rs;
      this.routines = [this.emptyRoutine, this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine ,this.emptyRoutine];
      this.orderRoutines();
    })
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

  removeRoutine() {
    this.routinePlanService.setRoutine(this.routineType, this.routines[this.selectedIndex])
  }


  routineUrls(): string[]{
    let urls: string[] = [];
    this.routinePlans.forEach(rp => urls.push(rp.routine));
    return urls;
  }

  orderRoutines(){
    this.routines = new Array<Routine>(this.tmpRoutines.length)
    this.tmpRoutines.forEach((r : Routine) => {
      let rpIndex = this.routinePlans.findIndex(rp => rp.routine.endsWith(r.id.toString()))
      if (rpIndex > -1){
        //let index = this.routinePlans[].index
        this.routines[rpIndex] = r;
      }
    })
  }
}
