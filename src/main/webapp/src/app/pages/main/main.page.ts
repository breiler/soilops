import { Component, OnInit } from '@angular/core';
import {DeviceService} from "../../service/device/device.service";
import {Device} from "../../model/device";
import * as moment from 'moment';

@Component({
  selector: 'app-main',
  templateUrl: './main.page.html',
  styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {
  private devices: Device[] = [];

  constructor(private deviceService: DeviceService) { }

  ngOnInit() {
    this.deviceService.getDevices().subscribe((devices : Device[]) => {
      this.devices = devices;
    });
  }

  fetchThingToken(thing: Device) {

  }

  formatDateTime(created: string) {
    return moment(created).fromNow();
  }

  doRefresh(event : any) {
    this.deviceService.getDevices().subscribe(() => {
      setTimeout(() => {
        event.target.complete();
      }, 1000);
    });
  }
}
