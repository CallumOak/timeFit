import {RoutinePlan} from "./routine-plan.model";
import {WeekDay} from "@angular/common";

export class WeeklyRoutinePlan extends RoutinePlan{
  public day!: WeekDay;
}
