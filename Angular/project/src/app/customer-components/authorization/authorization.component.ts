import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {
  public isSignIn: boolean = true;

  constructor() {

  }

  ngOnInit(): void {

  }

  onLoginSubmit(addForm: NgForm) {

  }

  onRegistrationSubmit(addForm: NgForm) {

  }
}
