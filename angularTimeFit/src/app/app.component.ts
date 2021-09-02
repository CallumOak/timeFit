import { Component } from '@angular/core';
import {TokenStorageService} from "./service/token-storage.service";
import {NavbarService} from "./service/navbar.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  constructor(private tokenStorageService: TokenStorageService,
              public navbarService: NavbarService,
              public router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signout();
    window.location.reload();
  }

  removeNavItem(name: string){
    if( this.router.url.includes((this.navbarService.getByName(name)).path)){
      this.router.navigate(["/home"])
    }
    this.navbarService.removeItem(name);
  }
}
