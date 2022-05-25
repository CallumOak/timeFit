import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {

  private _extraLinks = new Array<{ text: string, path: string }>();

  constructor() {
    console.log("New navbar service created !")
  }

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

  addItem(newText: string, newPath: string): number {
    let push: boolean = true;
    let returnedIndex = -1;
    this._extraLinks.forEach((value) => {
      if(value.text === newText){
        push = false;
        console.log("Link already present in navbar")
        returnedIndex = this._extraLinks.findIndex(v => value.text === v.text && value.path === v.path)
      }
    });
    if(push){
      console.log(`Previous links list length : ${this._extraLinks.length}`)
      this._extraLinks.push({ text: newText, path: newPath });
      console.log(`New links list length : ${this._extraLinks.length}`)
      returnedIndex = this._extraLinks.length - 1;
    }
    return returnedIndex;
  }


  removeItem(text: string) {
    this._extraLinks.forEach((link, index) => {
      if (link.text === text) {
        this._extraLinks.splice(index, 1);
      }
    });
  }

  editItem(index: number, text: string, path: string){
    let edit: boolean = true;
    this._extraLinks.forEach((value) => {
      if(value.text === text){
        edit = false;
        if(this._extraLinks[this._extraLinks.length - 1].text === ''){
          this._extraLinks.pop()
        }
      }
    });

    if(edit) {
      this._extraLinks[index].text = text
      this._extraLinks[index].path = path
    }
  }
}
