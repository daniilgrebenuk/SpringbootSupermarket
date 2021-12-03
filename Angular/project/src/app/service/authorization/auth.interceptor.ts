import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {Observable, throwError} from "rxjs";
import {environment} from "../../../environments/environment";
import {catchError} from "rxjs/operators";
import {Router} from "@angular/router";

const TOKEN_HEADER_KEY = environment.tokenHeaderKey;

@Injectable()
export class AuthInterceptor implements HttpInterceptor{
  constructor(
    private tokenStorage: TokenStorageService,
    private rout: Router
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.tokenStorage.getToken();
    if (token)
      req = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, token)});

    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) =>{
        if(err.status === 403) {
          if (token != null) {
            this.rout.navigateByUrl("employee/error")
          } else {
            this.rout.navigateByUrl("authorization")
          }
        }
        return throwError(err)
      })
    );
  }
}
