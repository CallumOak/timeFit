import {Deserializable} from "../interfaces/deserializable";
import {Routine} from "./routine.model";

export class Exercise /*implements Deserializable*/ {

  id!: string;
  name!: string;
  exerciseDuration! : string;
  restDuration! : string;
  repetitions! : string;
  exerciseColor! :string;
  restColor! :string;
  illustrationLocation! :string;
  position!: number;

  constructor() {
  }

  public setValues(id: string, name: string, exerciseDuration: string, restDuration: string, repetitions: string,
                   exerciseColor: string, restColor: string, illustrationLocation: string, position: number){
    this.id = id;
    this.name = name;
    this.exerciseDuration = exerciseDuration;
    this.restDuration = restDuration;
    this.repetitions = repetitions;
    this.exerciseColor = exerciseColor;
    this.restColor = restColor;
    this.illustrationLocation = illustrationLocation;
    this.position = position;
  }

/*  deserialize(input: any): this {
    Object.assign(this, input);
    return undefined;
  }*/
}
