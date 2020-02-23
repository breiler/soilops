import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../../model/user";
import {HttpClient} from "@angular/common/http";

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
