import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  form: any = {
    username: null,
    email: null,
    password: null
  };
  isSuccessful= false;
  isSignUpFailed = false;
  errorMessage= '';


  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void{
    const {username, email, password} = this.form;
    this.authService.signup(username, email, password).subscribe(
      data => {
        console.log(data);
        this.isSignUpFailed = false;
        this.isSuccessful = true;
      },
      error => {
        this.errorMessage = error.error.errorMessage;
        this.isSignUpFailed = true;
      }
    )
  }
}
