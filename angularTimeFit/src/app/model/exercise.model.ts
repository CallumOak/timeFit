import {Deserializable} from "../interfaces/deserializable";
import {Routine} from "./routine.model";

export class Exercise /*implements Deserializable*/ {

  private id!: string;
  private name!: string;
  private exerciseDuration! : string;
  private exerciseBreak! : string;
  private repetitions! :string;
  private exerciseColor! :string;
  private breakColor! :string;
  private illustrationLocation! :string;

/*  deserialize(input: any): this {
    Object.assign(this, input);
    return undefined;
  }*/
}
