import {Component, Input, OnInit} from '@angular/core';
import {Device} from "../../model/device";
import * as moment from "moment";
import {DeviceService} from "../../service/device/device.service";

@Component({
  selector: 'device',
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.scss'],
})
export class DeviceComponent implements OnInit {

  @Input() device:Device = new Device();

  constructor(private deviceService: DeviceService) { }

  ngOnInit() {}

  formatDateTime(created: string) {
    return moment(created).fromNow();
  }

  onChange() {
    this.deviceService.updateDevice(this.device).subscribe();
  }
}
