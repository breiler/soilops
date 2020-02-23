import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {DeviceService} from "../../service/device/device.service";
import {Device} from "../../model/device";
import {Observable} from "rxjs";
import * as moment from "moment";
import {StatisticsService} from "../../service/service/statistics.service";
import {Statistics} from "../../model/statistics";

@Component({
  selector: 'app-device',
  templateUrl: './device.page.html',
  styleUrls: ['./device.page.scss'],
})
export class DevicePage implements OnInit {
  private device: Device;
  private statistics: Statistics;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private deviceService: DeviceService,
              private statisticsService: StatisticsService) {
  }

  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.deviceService.getDevice(params.get('id'))))
      .subscribe((device: Device) => {
        this.device = device;
      });

    this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.statisticsService.getDevice(params.get('id'))))
      .subscribe((statistics: Statistics) => {
        this.statistics = statistics;
      });
  }

  formatDateTime(created: string) {
    return moment(created).fromNow();
  }

  onChange() {
    this.deviceService.updateDevice(this.device).subscribe();
  }
}
