import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';
import {Observable, BehaviorSubject, Subject} from 'rxjs';

import {Storage} from '@ionic/storage';
import {User} from '../../model/user';
import {AuthResponse} from "../../model/auth-response";
import {AuthRequest} from "../../model/auth-request";
import jwt_decode from "jwt-decode"

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private endpoint: string = '/api/authenticate';
  private userSubject: Subject<User> = new Subject<User>();
  private user: User;

  constructor(private  httpClient: HttpClient, private  storage: Storage) {
    storage.get("user").then(user => {
      this.user = user;
      this.userSubject.next(user);
    });
  }

  public login(username: string, password: string): Observable<AuthResponse> {
    let authRequest = new AuthRequest();
    authRequest.username = username;
    authRequest.password = password;

    return this.httpClient.post(`${this.endpoint}`, authRequest)
      .pipe(
        map((res: AuthResponse) => {
          let parsedToken = jwt_decode(res.token);
          this.user = new User();
          this.user.token = res.token;
          this.user.username = parsedToken.sub;
          this.storage.set("user", this.user);
          return this.user;
        }),
        tap((user: User) => {
          this.userSubject.next(this.user);
        }));
  }

  public logout() {
    this.storage.remove("user");
    this.userSubject.next(null);
  }

  public getSubject(): Subject<User> {
    return this.userSubject;
  }
}
