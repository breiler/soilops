import { Component, OnInit } from '@angular/core';
import {ModalController} from "@ionic/angular";
import {Device} from "../../model/device";
import {DeviceService} from "../../service/device/device.service";

@Component({
  selector: 'app-token',
  templateUrl: './token.page.html',
  styleUrls: ['./token.page.scss'],
})
export class TokenPage implements OnInit {
  generatedToken: string;
  device: Device;

  constructor(public modalController: ModalController, private thingsService: DeviceService) { }

  ngOnInit() {
  }

  dismiss() {
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  generate() {
    this.thingsService
      .getThingToken(this.device.uuid)
      .subscribe(token => this.generatedToken = token);
  }
}
