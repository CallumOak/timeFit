import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent{

  user: User;
  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router) {
    this.user = new User();
  }

  // onSubmit(){
  //   this.userService.save(this.user).subscribe(result =>{
  //     this.goToUserList();
  //   });
  // }

  goToUserList(){
    this.router.navigate(["/users"]);
  }

}
