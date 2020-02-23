import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {Device} from "../../model/device";
import {map, tap} from "rxjs/operators";
import {RegisterRequest} from "../../model/register-request";
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private devicesSubject: BehaviorSubject<Device[]>;
  private devicesObserver: Observable<Device[]>;

  constructor(private httpClient: HttpClient) {
    this.devicesSubject = new BehaviorSubject<Device[]>([]);
    this.devicesObserver = this.devicesSubject.asObservable();
  }

  public getDevices(): Observable<Device[]> {
    const token = localStorage.getItem('token');
    this.httpClient.get("/api/devices", {
      headers: {
        "Authorization": "Bearer " + token
      }
    }).subscribe((devices: Device[]) => {
      this.devicesSubject.next(devices);

    });

    return this.devicesObserver;
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
          this.getDevices();
          return res;
        }));
  }

  updateDevice(device: Device) {
    const token = localStorage.getItem('token');
    let updateDeviceRequest = {
      uuid: device.uuid,
      name: device.name
    };

    return this.httpClient.put("/api/devices/" + device.uuid, updateDeviceRequest, {
      headers: {
        "Authorization": "Bearer " + token
      }
    })
      .pipe(
        map((res: Device) => {
          this.getDevices();
          return res;
        }));
  }

  getDevice(uuid: string) : Observable<Device> {
    const token = localStorage.getItem('token');
    return this.httpClient.get("/api/devices/" + uuid, {
      headers: {
        "Authorization": "Bearer " + token
      }
    }).pipe(
      map((res:Device) => {
        return res;
      })
    );
  }
}
