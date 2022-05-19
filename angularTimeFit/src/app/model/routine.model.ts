import {Deserializable} from "../interfaces/deserializable";

export class Routine /*implements Deserializable*/ {
  id!: string;
  user!: string;
  name!: string;
  numberOfCycles!: number;
  color! : string;
  exercises : string[] = [];
  routinePlans : string[] = [];
/*  deserialize(input: any): this {
    return undefined;
  }*/
}
