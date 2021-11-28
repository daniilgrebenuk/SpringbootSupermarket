import {Component, OnInit} from '@angular/core';
import {Employee} from "../../model/employee";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-employee-manager',
  templateUrl: './employee-manager.component.html',
  styleUrls: ['./employee-manager.component.css']
})

export class EmployeeManagerComponent implements OnInit {

  public arr: Employee[];
  public searchOption: string = "";
  public currentEmployee: Employee | null = null;

  constructor() {
    this.arr = [
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "1",user: {id: 1}},
      {
        id: 3,
        name: "Bob",
        surname: "Top",
        imageUrl: "https://icdn.lenta.ru/images/2021/04/27/16/20210427163138131/square_320_c09ebae17387b7d6eeb9fa0d42afe5ee.jpg",
        position: "3",
        user: {id: 1}
      },
      {id: 4, name: "Bob", surname: "Top", imageUrl: "", position: "manager",user: {id: 1}},
      {id: 4, name: "Bob", surname: "Top", imageUrl: "", position: "manager",user: {id: 1}},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager",user: {id: 1}},
    ];
  }

  ngOnInit(): void {
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
    return this.arr.filter(e => e.surname.includes(this.searchOption) || e.position.includes(this.searchOption));
  }

  clear() {
    this.searchOption = "";
  }

  onAddClick(){
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

  onAddEmployee(addForm: NgForm) {

  }
}
