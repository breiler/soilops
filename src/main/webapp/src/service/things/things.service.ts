import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Storage} from "@ionic/storage";
import {AuthService} from "../auth/auth.service";
import {User} from "../../model/user";
import {Observable, Subject} from "rxjs";
import {Thing} from "../../model/thing";
import {AuthResponse} from "../../model/auth-response";
import {AuthRequest} from "../../model/auth-request";
import {map, tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ThingsService {

  private user: User;
  private endpoint: string = '/api/things';
  private thingsSubject: Subject<Thing[]> = new Subject<Thing[]>();

  constructor(private  httpClient: HttpClient, private  storage: Storage, private authService: AuthService) {
    authService.getSubject().subscribe(user => {
      this.user = user;
      this.getThings().subscribe();
    });
  }

  public getThings(): Observable<Thing[]> {

    return this.httpClient.get(`${this.endpoint}`, {
      headers: {
        "Authorization": "Bearer " + this.user.token
      }
    })
      .pipe(
        map((res: Thing[]) => {
          return res;
        }),
        tap((things: Thing[]) => {
          this.thingsSubject.next(things);
        }));
  }

  public getSubject(): Subject<Thing[]> {
    return this.thingsSubject;
  }

}
