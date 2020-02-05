import {Component} from '@angular/core';
import {ModalController, NavController} from "@ionic/angular";
import {LoginPage} from "../login/login.page";
import {AuthService} from "../../service/auth/auth.service";
import {User} from "../../model/user";
import {ThingsService} from "../../service/things/things.service";
import {Thing} from "../../model/thing";
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
  private things: Thing[];

  constructor(private modalController: ModalController, private authService: AuthService, private thingsService: ThingsService, private websocketService: WebsocketService) {
    thingsService.getSubject().subscribe((things: Thing[]) => {
      this.things = things
    });

    authService.getSubject().subscribe((user: User) => {
      this.user = user;
    });
  }

  async login() {
    const modal = await this.modalController.create({
      component: LoginPage
    });
    return await modal.present();
  }

  logout() {
    this.authService.logout();
  }

  async fetchThingToken(thing: Thing) {
    const modal = await this.modalController.create({
      component: TokenPage,
      componentProps: {
        thing: thing
      }
    });
    return await modal.present();
  }

  formatDateTime(dateTime: any) {
    return moment(dateTime).fromNow();
  }

  doRefresh(event) {
    this.thingsService.getThings().subscribe(() => {
      setTimeout(() => {
        event.target.complete();
      }, 1000);
    });
  }
}
