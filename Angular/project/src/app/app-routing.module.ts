import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmployeeManagerComponent} from "./employee-components/employee-manager/employee-manager.component";
import {AuthorizationComponent} from "./customer-components/authorization/authorization.component";
import {ErrorPageComponent} from "./employee-components/error-page/error-page.component";
import {ProductManagerComponent} from "./employee-components/product-manager/product-manager.component";
import {StorageManagerComponent} from "./employee-components/storage-manager/storage-manager.component";
import {SupplyManagerComponent} from "./employee-components/supply-manager/supply-manager.component";

const routes: Routes = [
  {path: 'employee', component: EmployeeManagerComponent},
  {path: 'authorization', component: AuthorizationComponent},
  {path: 'error', component: ErrorPageComponent},
  {path: 'product', component: ProductManagerComponent},
  {path: 'storage', component: StorageManagerComponent},
  {path: 'storage/:idSupply', component: SupplyManagerComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
