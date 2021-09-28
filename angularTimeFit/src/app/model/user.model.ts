import {Deserializable} from "../interfaces/deserializable";

export class User /*implements Deserializable*/ {
  id!: string;
  firstName!: string;
  email!: string;

/*  deserialize(input: any): this {
    return undefined;
  }*/
}
