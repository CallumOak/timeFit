import {Program} from "./program.model";
import {Routine} from "./routine.model";
import {Time} from "@angular/common";

export class RoutinePlan {
  public id!: string;
  public program!: Program;
  public routine!: Routine;
  public startTime!: Time;
  public endTime!: Time;
}
