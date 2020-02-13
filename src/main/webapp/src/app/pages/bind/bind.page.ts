import {Component, OnInit} from '@angular/core';
import {DeviceService} from "../../service/device/device.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-bind',
  templateUrl: './bind.page.html',
  styleUrls: ['./bind.page.scss'],
})
export class BindPage implements OnInit {
  private pin: string;
  private error: string;

  constructor(private deviceService: DeviceService, private router: Router) {
  }

  ngOnInit() {
  }

  bind() {
    this.error = null;
    this.deviceService.registerDevice(this.pin).subscribe(() => {
      this.router.navigateByUrl("/")
      this.pin = "";
    }, () => {
      this.error = "Couldn't register the device";
    });
  }
}
