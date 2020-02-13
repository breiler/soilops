import {Component} from '@angular/core';
import {User} from "../../model/user";
import {Device} from "../../model/device";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  private user: User;
  private things: Device[];

  constructor() {
  }
}
