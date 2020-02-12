import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../model/user";
import {Observable, Subject} from "rxjs";
import {Device} from "../../model/device";
import {map, tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private user: User;
  private endpoint: string = '/api/devices';
  private thingsSubject: Subject<Device[]> = new Subject<Device[]>();

  constructor(private  httpClient: HttpClient) {

  }

  public getDevices(): Observable<Device[]> {
    const token = localStorage.getItem('token');
    return this.httpClient.get(`${this.endpoint}`, {
        headers: {
          "Authorization": "Bearer " + token
        }
      })
      .pipe(
        map((res: Device[]) => {
          return res;
        }),
        tap((things: Device[]) => {
          this.thingsSubject.next(things);
        }));
  }

  public getSubject(): Subject<Device[]> {
    return this.thingsSubject;
  }

  public getThingToken(uuid: string): Observable<string> {
    return this.httpClient.post(this.endpoint + "/" + uuid + "/token", null, {
      headers: {
        "Authorization": "Bearer " + this.user.token
      }
    })
      .pipe(
        map((res: any) => {
          return res.token;
        }));
  }

}
