import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmployeeManagerComponent} from "./employee-components/employee-manager/employee-manager.component";
import {AuthorizationComponent} from "./customer-components/authorization/authorization.component";
import {ErrorPageComponent} from "./employee-components/error-page/error-page.component";

const routes: Routes = [
  {path: 'employee', component: EmployeeManagerComponent},
  {path: 'authorization', component: AuthorizationComponent},
  {path: 'employee/error', component: ErrorPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
