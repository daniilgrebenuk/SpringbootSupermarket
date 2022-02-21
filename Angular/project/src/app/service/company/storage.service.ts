import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private storageUrl = environment.storageApiUrl;

  constructor(private http: HttpClient) {
  }

  public getAllStorage(): Observable<any> {
    return this.http.get(`${this.storageUrl}/all`);
  }

  public addStorage(storageLocation: string): Observable<any> {
    return this.http.post(`${this.storageUrl}/add`, {location: storageLocation});
  }
}
