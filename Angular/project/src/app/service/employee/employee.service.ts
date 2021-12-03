import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Employee} from "../../model/employee";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private employeeUrl = environment.employeeApiUrl;

  constructor(private http: HttpClient) {
  }

  public getAllEmployee(): Observable<any> {
    return this.http.get(this.employeeUrl + '/all');
  }

  public addEmployee(employee: Employee): Observable<any> {
    return this.http.post(this.employeeUrl + '/add', employee);
  }
}
