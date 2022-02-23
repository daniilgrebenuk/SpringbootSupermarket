import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {SupplyService} from "../../service/company/supply.service";
import {EmployeeService} from "../../service/company/employee.service";
import {ProductService} from "../../service/company/product.service";
import {Supply} from "../../model/supply";
import {Employee} from "../../model/employee";
import {Product} from "../../model/product";

@Component({
  selector: 'app-supply-changer',
  templateUrl: './supply-changer.component.html',
  styleUrls: ['./supply-changer.component.css']
})
export class SupplyChangerComponent implements OnInit {

  private supplyId: number = -1;
  supply: Supply | null = null;
  employees: Employee[] = [];
  products: Product[] = [];

  constructor(
    private rout: ActivatedRoute,
    private supplyService: SupplyService,
    private employeeService: EmployeeService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.rout.params.subscribe(
      param => {
        this.supplyId = param["idSupply"];
        this.initSupply(this.supplyId);
      }
    ).unsubscribe();
  }

  private initSupply(supplyId: number) {
    this.initDefaultSupplyInformation(supplyId);
    this.initEmployee(supplyId);
    this.initProducts(supplyId);
  }

  private initDefaultSupplyInformation(supplyId: number){
    this.supplyService.getSupplyBySupplyId(supplyId).subscribe(
      resp=> {
        this.supply = resp;
      }
    );

  }

  private initEmployee(supplyId: number) {
    this.employeeService.getAllBySupplyId(supplyId).subscribe(
      resp=>this.employees = resp
    )
  }

  private initProducts(supplyId: number) {
    this.productService.getAllProductBySupplyId(supplyId).subscribe(
      resp => {
        this.products = resp
      }
    )
  }
}
