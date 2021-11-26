import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {EmployeeManagerComponent} from "./employee-manager/employee-manager.component";

const routes: Routes = [
  { path: 'employee', component: EmployeeManagerComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
