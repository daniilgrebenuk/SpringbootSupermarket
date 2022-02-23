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

  public getAll(): Observable<any> {
    return this.http.get(`${this.employeeUrl}/all`);
  }

  public getAllBySupplyId(supplyId: number): Observable<any>{
    return this.http.get(`${this.employeeUrl}/get/${supplyId}`);
  }

  public add(employee: Employee): Observable<any> {
    return this.http.post(`${this.employeeUrl}/add`, employee);
  }

  public update(employee: Employee): Observable<any> {
    if(employee.id < 0){
      throw new Error();
    }
    return this.http.put(`${this.employeeUrl}/update`, employee);
  }

  public delete(id: number): Observable<any>{
    return this.http.delete(`${this.employeeUrl}/delete?id=${id}`)
  }

  public roles(): Observable<any>{
    return this.http.get(`${this.employeeUrl}/all-roles`);
  }
}
