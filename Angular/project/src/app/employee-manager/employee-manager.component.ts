import {Component, OnInit} from '@angular/core';
import {Employee} from "../model/employee";

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
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "1"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "2"},
      {
        id: 3,
        name: "Bob",
        surname: "Top",
        imageUrl: "https://cdn-icons.flaticon.com/png/512/706/premium/706807.png?token=exp=1637880413~hmac=85c007f2cf1a6c8ef5a95d793d828525",
        position: "3"
      },
      {id: 4, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 4, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"},
      {id: 1, name: "Bob", surname: "Top", imageUrl: "", position: "manager"}
    ];
  }

  ngOnInit(): void {
  }

  openCardInfo(employee: Employee) {
    this.currentEmployee = employee;

    let modal = document.getElementById('modal');
    let modalBody = document.getElementById('modal-body');

    modal!.style.opacity = '1';
    modal!.style.zIndex = '1';
    modalBody!.style.top = "3em";
    modalBody!.style.bottom = "3em";
  }

  onCloseCardInfo() {
    let modal = document.getElementById('modal');
    let modalBody = document.getElementById('modal-body');

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
}
