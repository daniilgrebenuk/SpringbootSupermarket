import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthService} from "../../service/authorization/auth.service";
import {TokenStorageService} from "../../service/authorization/token-storage.service";
import {environment} from "../../../environments/environment";
import {Location} from "@angular/common";

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {
  public isSignIn: boolean = true;
  private authHeader = environment.tokenHeaderKey;

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private location: Location
  ) {

  }

  ngOnInit(): void {

  }

  onLoginSubmit(form: NgForm) {
    this.authService.login(form.value).subscribe(
      resp => {
        console.log(resp);
        this.tokenStorage.setToken(resp.accessToken, true);
        this.location.back();
      }
    )
  }

  onRegistrationSubmit(form: NgForm) {
    this.authService.register(form.value).subscribe(
      resp => {
        this.isSignIn = true;
      }
    )
  }
}
