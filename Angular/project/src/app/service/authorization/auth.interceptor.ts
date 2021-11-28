import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

const TOKEN_HEADER_KEY = environment.tokenHeaderKey;

@Injectable()
export class AuthInterceptor implements HttpInterceptor{
  constructor(private tokenStorage: TokenStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.tokenStorage.getToken();
    console.log("hub")
    if (token != null)
      return next.handle(req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, token)}));

    return next.handle(req);
  }
}
