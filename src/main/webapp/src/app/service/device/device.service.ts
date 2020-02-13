import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {Device} from "../../model/device";
import {map, tap} from "rxjs/operators";
import {RegisterRequest} from "../../model/register-request";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private deviceSubject: Subject<Device[]> = new Subject<Device[]>();

  constructor(private httpClient: HttpClient) {

  }

  public getDevices(): Observable<Device[]> {
    const token = localStorage.getItem('token');
    return this.httpClient.get("/api/devices", {
      headers: {
        "Authorization": "Bearer " + token
      }
    })
      .pipe(
        map((res: Device[]) => {
          return res;
        }),
        tap((devices: Device[]) => {
          this.deviceSubject.next(devices);
        }));
  }

  public registerDevice(pin: string): Observable<Device> {
    const token = localStorage.getItem('token');
    let registerRequest: RegisterRequest = new RegisterRequest();
    registerRequest.pin = pin;

    return this.httpClient.put("/api/discovery", registerRequest, {
      headers: {
        "Authorization": "Bearer " + token
      }
    })
      .pipe(
        map((res: Device) => {
          return res;
        }));
  }

}
