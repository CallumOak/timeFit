import { RoutinePlan } from "./routine-plan.model";

export class IndividualRoutinePlan extends RoutinePlan{
  public type: string = "individual";
  public date!: Date;
}
