import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {Device} from "../../model/device";
import {User} from "../../model/user";
import {HttpClient} from "@angular/common/http";
import {AuthResponse} from "../../model/auth-response";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userSubject: BehaviorSubject<User>;
  private userObserver: Observable<User>;

  constructor(private httpClient: HttpClient) {
    this.userSubject = new BehaviorSubject<User>(null);
    this.userObserver = this.userSubject.asObservable();
  }

  getUser(): Observable<User> {
    return this.userObserver;
  }

  refreshUser() {
    this.httpClient.get(`/api/users/current`)
      .subscribe((res: User) => {
        this.userSubject.next(res);
      });
  }
}
