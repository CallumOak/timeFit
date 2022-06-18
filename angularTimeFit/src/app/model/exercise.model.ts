import {Deserializable} from "../interfaces/deserializable";
import {Routine} from "./routine.model";

export class Exercise /*implements Deserializable*/ {

  id!: string;
  name!: string;
  exerciseDuration! : string;
  exerciseBreak! : string;
  repetitions! : string;
  exerciseColor! :string;
  restColor! :string;
  illustrationLocation! :string;
  routines: string[] = [];
  positions: number[] = [];

  constructor() {
  }

  public setValues(id: string, name: string, exerciseDuration: string, exerciseBreak: string, repetitions: string,
                   exerciseColor: string, restColor: string, illustrationLocation: string, positions: number[]){
    this.id = id;
    this.name = name;
    this.exerciseDuration = exerciseDuration;
    this.exerciseBreak = exerciseBreak;
    this.repetitions = repetitions;
    this.exerciseColor = exerciseColor;
    this.restColor = restColor;
    this.illustrationLocation = illustrationLocation;
    this.positions = positions;
  }

/*  deserialize(input: any): this {
    Object.assign(this, input);
    return undefined;
  }*/
}
