import {Component} from '@angular/core';
import {ModalController, NavController} from "@ionic/angular";
import {LoginPage} from "../login/login.page";
import {AuthService} from "../../service/auth/auth.service";
import {User} from "../../model/user";
import {DeviceService} from "../../service/device/device.service";
import {Device} from "../../model/device";
import * as moment from 'moment';
import {WebsocketService} from "../../service/websocket/websocket.service";
import {TokenPage} from "../token/token.page";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  private user: User;
  private things: Device[];

  constructor(private modalController: ModalController, private authService: AuthService, private thingsService: DeviceService, private websocketService: WebsocketService) {
    //thingsService.getThings().subscribe();
  }
}
