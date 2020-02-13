import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';
import {Observable, BehaviorSubject, Subject} from 'rxjs';

import {User} from '../../model/user';
import {AuthResponse} from "../../model/auth-response";
import {AuthRequest} from "../../model/auth-request";
import {JwtHelperService} from '@auth0/angular-jwt';
import decode from 'jwt-decode';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private endpoint: string = '/api/auth/token';
  private userSubject: Subject<User> = new Subject<User>();
  private tokenSubject: Subject<string> = new Subject<string>();
  private user: User;
  private token: string;
  private authenticatedSubject: BehaviorSubject<boolean>;
  private authenticatedObserver: Observable<boolean>;

  constructor(private httpClient: HttpClient, private router: Router) {
    this.authenticatedSubject = new BehaviorSubject<boolean>(false);
    this.authenticatedObserver = this.authenticatedSubject.asObservable();
  }

  /**
   * Check whether the token is expired and return true or false
   */
  public isAuthenticated(): Observable<boolean> {
    const token = localStorage.getItem('token');
    if (token !== null) {
      try {
        this.authenticatedSubject.next(!new JwtHelperService().isTokenExpired(token));
      } catch (error) {
        localStorage.removeItem('token');
        this.router.navigateByUrl("/login");
      }
    } else {
      this.httpClient.get(`${this.endpoint}`)
        .subscribe((res: AuthResponse) => {
          localStorage.setItem('token', res.token);
          this.authenticatedSubject.next(!new JwtHelperService().isTokenExpired(res.token));
          this.router.navigateByUrl("/");
        });
    }

    return this.authenticatedObserver;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigateByUrl("/");
    this.httpClient.get("/api/logout").subscribe();
    this.authenticatedSubject.next(false);
  }
}
