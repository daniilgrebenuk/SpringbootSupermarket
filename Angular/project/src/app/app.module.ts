import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {EmployeeManagerComponent} from './employee-components/employee-manager/employee-manager.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthorizationComponent} from './customer-components/authorization/authorization.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor} from "./service/authorization/auth.interceptor";
import { ErrorPageComponent } from './employee-components/error-page/error-page.component';
import { ProductManagerComponent } from './employee-components/product-manager/product-manager.component';
import { StorageManagerComponent } from './employee-components/storage-manager/storage-manager.component';
import { SupplyManagerComponent } from './employee-components/supply-manager/supply-manager.component';
import { SupplyChangerComponent } from './employee-components/supply-changer/supply-changer.component';

@NgModule({
  declarations: [
    AppComponent,
    EmployeeManagerComponent,
    AuthorizationComponent,
    ErrorPageComponent,
    ProductManagerComponent,
    StorageManagerComponent,
    SupplyManagerComponent,
    SupplyChangerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
