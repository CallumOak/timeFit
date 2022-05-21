import {RoutinePlan} from "./routine-plan.model";

export class FrequencyRoutinePlan extends RoutinePlan{
  public type: string = "frequency";
  public index!: number;
}
