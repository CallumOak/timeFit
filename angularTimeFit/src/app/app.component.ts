import {Component, OnDestroy, OnInit} from '@angular/core';
import {TokenStorageService} from "./service/token-storage.service";
import {NavbarService} from "./service/navbar.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {ProgramService} from "./service/program.service";
import {Program} from "./model/program.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy{
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;
  program?: Program;
  programSubscription: Subscription = new Subscription();

  constructor(private tokenStorageService: TokenStorageService,
              public navbarService: NavbarService,
              public router: Router,
              private programService: ProgramService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }

    this.programSubscription = this.programService.program$.subscribe(p => this.program = p)
  }

  logout(): void {
    this.tokenStorageService.signout();
    window.location.reload();
    this.router.navigate(['/home'])
  }

  removeNavItem(name: string){
    if( this.router.url.includes((this.navbarService.getByName(name)).path)){
      this.router.navigate(["/calendar"])
    }
    this.navbarService.removeItem(name);
  }

  programLink(): string {
    return this.program === undefined ? "" : "program/" + this.program.programSetting + "Program";
  }

  ngOnDestroy(): void {
    this.programSubscription.unsubscribe();
  }
}
