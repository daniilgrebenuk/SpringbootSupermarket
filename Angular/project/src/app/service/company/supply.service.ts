import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SupplyService {

  private supplyUrl = environment.supplyApiUrl;

  constructor(private http: HttpClient) {
  }

  public getSupplyByStorageId(id: number): Observable<any> {
    return this.http.get(`${this.supplyUrl}/all-by-storage-id/${id}`);
  }

  addSupply(storageId: number, date: string): Observable<any> {
    return this.http.post(`${this.supplyUrl}/add`, {storageId: storageId, date: date});
  }
}
