import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";

const TOKEN_KEY = environment.tokenStorageKey;

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() {
  }

  sighOut() {
    localStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(TOKEN_KEY);
  }

  getToken(): string | null {
    let token = localStorage.getItem(TOKEN_KEY);
    return token != null ? token : sessionStorage.getItem(TOKEN_KEY);
  }

  setToken(token: string, saveInLocalMemory: boolean = false) {
    if (saveInLocalMemory)
      localStorage.setItem(TOKEN_KEY, token);
    else
      localStorage.setItem(TOKEN_KEY, token);
  }
}
