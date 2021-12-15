import {Component, OnInit} from '@angular/core';
import {Employee} from "../../model/employee";
import {NgForm} from "@angular/forms";
import {EmployeeService} from "../../service/employee/employee.service";
import {Router, RouterModule} from "@angular/router";
import {Role, User} from "../../model/user";

@Component({
  selector: 'app-employee-manager',
  templateUrl: './employee-manager.component.html',
  styleUrls: ['./employee-manager.component.css']
})

export class EmployeeManagerComponent implements OnInit {

  public employees: Employee[] = [];
  public roles: Role[] = [];
  public searchOption: string = "";
  public currentEmployee: Employee | null = null;
  public userInfo: User | null = null;

  constructor(private employeeService: EmployeeService, private rout: Router) {

  }

  ngOnInit(): void {
    this.initEmployee()
    this.initRoles()
  }

  private initEmployee() {
    this.employeeService.getAll().subscribe(
      resp => this.employees = resp
    );
  }

  private initRoles() {
    this.employeeService.roles().subscribe(
      resp => this.roles = resp
    );
  }


  public openCardInfo(employee: Employee) {
    this.currentEmployee = employee;
    this.userInfo = employee.user;
    console.log(this.userInfo)
    this.openModal();
  }

  openModal() {
    let modal = document.getElementById('modal-add');
    let modalBody = document.getElementById('modal-add-body');

    modal!.style.opacity = '1';
    modal!.style.zIndex = '2';
    modalBody!.style.top = "3em";
    modalBody!.style.bottom = "3em";
  }

  closeModal() {
    this.currentEmployee = null;
    this.userInfo = null;

    let modal = document.getElementById('modal-add');
    let modalBody = document.getElementById('modal-add-body');

    modal!.style.opacity = '0';
    modal!.style.zIndex = '-1';
    modalBody!.style.top = "4em";
    modalBody!.style.bottom = "2em";
  }


  getEmployeeWithSearch(): Employee[] {
    return this.employees.filter(e => e.surname.includes(this.searchOption));
  }

  clearSearch() {
    this.searchOption = "";
  }


  onSaveEmployee(form: NgForm) {
    let role = this.roles.filter(r => r.authority === form.value.role)[0];
    let employee: Employee = form.value;
    employee.user = {id: form.value.userId, role: role};
    employee.salary = Number(employee.salary.toString().replace(/ +/, ""));
    if (this.currentEmployee) {
      employee.id = this.currentEmployee.id;
      this.employeeService.update(employee).subscribe(
        resp => {
          console.log(resp);
          this.initEmployee();
        }
      );
    }
    else {
      this.employeeService.add(employee).subscribe(
        resp => {
          console.log(resp);
          this.initEmployee();
        }
      );
    }
    this.closeModal();
  }


  onDeleteEmployee() {
    if (!this.currentEmployee) {
      return;
    }
    this.employeeService.delete(this.currentEmployee.id).subscribe(
      resp=>{
        this.initEmployee();
      }
    );
    this.closeModal();
  }
}
