import {Deserializable} from "../interfaces/deserializable";

export class Routine {
  id!: string;
  user!: string;
  name!: string;
  numberOfCycles!: number;
  color! : string;
  exercises : string[] = [];
  exercisePositions: number[] = [];
  weeklyRoutinePlans : string[] = [];
  frequencyRoutinePlans : string[] = [];
  individualRoutinePlans : string[] = [];
  illustrationLocation! : string;
}
