import {Component, OnInit} from '@angular/core';
import {Employee} from "../../model/employee";
import {NgForm} from "@angular/forms";
import {EmployeeService} from "../../service/employee/employee.service";
import {Router, RouterModule} from "@angular/router";

@Component({
  selector: 'app-employee-manager',
  templateUrl: './employee-manager.component.html',
  styleUrls: ['./employee-manager.component.css']
})

export class EmployeeManagerComponent implements OnInit {

  public arr: Employee[] = [];
  public searchOption: string = "";
  public currentEmployee: Employee | null = null;

  constructor(private employeeService: EmployeeService, private rout: Router) {

  }

  ngOnInit(): void {
    this.employeeService.getAllEmployee().subscribe(
      resp => {
        this.arr = resp
      }
    )
  }

  openCardInfo(employee: Employee) {
    this.currentEmployee = employee;

    let modal = document.getElementById('modal-change');
    let modalBody = document.getElementById('modal-change-body');

    modal!.style.opacity = '1';
    modal!.style.zIndex = '1';
    modalBody!.style.top = "3em";
    modalBody!.style.bottom = "3em";
  }

  onCloseCardInfo() {
    let modal = document.getElementById('modal-change');
    let modalBody = document.getElementById('modal-change-body');

    modal!.style.opacity = '0';
    modal!.style.zIndex = '-1';
    modalBody!.style.top = "4em";
    modalBody!.style.bottom = "2em";
  }


  getEmployeeWithSearch(): Employee[] {
    return this.arr.filter(e => e.surname.includes(this.searchOption));
  }

  clear() {
    this.searchOption = "";
  }

  onAddClick() {
    let modal = document.getElementById('modal-add');
    let modalBody = document.getElementById('modal-add-body');

    modal!.style.opacity = '1';
    modal!.style.zIndex = '2';
    modalBody!.style.top = "3em";
    modalBody!.style.bottom = "3em";
  }

  onCloseAddModal() {
    let modal = document.getElementById('modal-add');
    let modalBody = document.getElementById('modal-add-body');

    modal!.style.opacity = '0';
    modal!.style.zIndex = '-1';
    modalBody!.style.top = "4em";
    modalBody!.style.bottom = "2em";
  }

  onAddEmployee(form: NgForm) {
    let employee: Employee = form.value;
    employee.user = {id: form.value.userId, role: {id: 1, authority: null}};
    employee.salary = Number(employee.salary.toString().replace(/ +/,""));
    console.log(employee.salary)
    this.employeeService.addEmployee(employee).subscribe(
      resp=>console.log(resp)
    );
  }
}
