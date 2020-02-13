import {Component, OnDestroy, OnInit} from '@angular/core';
import {DeviceService} from "../../service/device/device.service";
import {Device} from "../../model/device";
import * as moment from 'moment';
import {UserService} from "../../service/user/user.service";
import {Subscription} from "rxjs";
import {User} from "../../model/user";

@Component({
  selector: 'app-main',
  templateUrl: './main.page.html',
  styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit, OnDestroy {
  private devices: Device[] = [];
  private devicesSubscription: Subscription;
  private userSubscription: Subscription;
  private user: User;

  constructor(private deviceService: DeviceService, private userService: UserService) {
  }

  ngOnInit() {
    this.devicesSubscription = this.deviceService.getDevices().subscribe((devices: Device[]) => {
      this.devices = devices;
    });

    this.userSubscription = this.userService.getUser().subscribe((user: User) => {
      this.user = user
    });
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
    this.devicesSubscription.unsubscribe();
  }

  formatDateTime(created: string) {
    return moment(created).fromNow();
  }

  doRefresh(event: any) {
    this.deviceService.getDevices().subscribe(() => {
      setTimeout(() => {
        event.target.complete();
      }, 1000);
    });

    this.userService.refreshUser();
  }
}
