import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {EmployeeManagerComponent} from './employee-components/employee-manager/employee-manager.component';
import {FormsModule} from "@angular/forms";
import {AuthorizationComponent} from './customer-components/authorization/authorization.component';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {AuthInterceptor} from "./service/authorization/auth.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    EmployeeManagerComponent,
    AuthorizationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
