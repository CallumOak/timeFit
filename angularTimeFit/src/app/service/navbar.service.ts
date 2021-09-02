import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {

  private _extraLinks = new Array<{ text: string, path: string }>();

  constructor() { }

  get extraLinks(): { text: string; path: string }[] {
    return this._extraLinks;
  }

  getByName(name: string){
    let retVal = {text: "***",path: "***"};
    this._extraLinks.forEach((value) => {
      if(value.text === name){
        retVal = value;
      }
    })
    return retVal;
  }

  addItem(newText: string, newPath: string) {
    let push: boolean = true;
    this._extraLinks.forEach((value) => {
      if(value.text === newText){
        push = false;
      }
    });
    if(push){
      this._extraLinks.push({ text: newText, path: newPath });
    }
  }


  removeItem(text: string) {
    this._extraLinks.forEach((link, index) => {
      if (link.text === text) {
        this._extraLinks.splice(index, 1);
      }
    });
  }
}
