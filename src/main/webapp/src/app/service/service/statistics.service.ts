import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Device} from "../../model/device";
import {map} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";
import {Statistics} from "../../model/statistics";

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(private httpClient: HttpClient) { }

  getDevice(uuid: string) : Observable<Statistics> {
    const token = localStorage.getItem('token');
    return this.httpClient.get("/api/statistics/devices/" + uuid, {
      headers: {
        "Authorization": "Bearer " + token
      }
    }).pipe(
      map((res:Statistics) => {
        return res;
      })
    );
  }
}
