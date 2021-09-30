import {Deserializable} from "../interfaces/deserializable";

export class Routine /*implements Deserializable*/ {
  id!: string;
  program!: string;
  routine!: string;
  startTime!: string;
  endTime!: string;
/*  deserialize(input: any): this {
    return undefined;
  }*/
}
