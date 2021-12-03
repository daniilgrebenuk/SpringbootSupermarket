import {Injectable} from '@angular/core';
import {User} from "../../model/user";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl = environment.authApiUrl;

  constructor(private http: HttpClient) {
  }

  login(user: User): Observable<any> {
    return this.http.post(this.authUrl + '/login', user);
  }

  register(user: User): Observable<any> {
    return this.http.post(this.authUrl + '/registration', user);
  }
}
