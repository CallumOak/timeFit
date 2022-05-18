import {Program} from "./program.model";
import {Routine} from "./routine.model";
import {Time} from "@angular/common";

export class RoutinePlan {
  public id!: string;
  public program!: string;
  public routine!: string;
  public startTime!: Time;
  public endTime!: Time;
}
