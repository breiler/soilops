import { Component, OnInit } from '@angular/core';
import {ModalController} from "@ionic/angular";
import {Thing} from "../../model/thing";
import {ThingsService} from "../../service/things/things.service";

@Component({
  selector: 'app-token',
  templateUrl: './token.page.html',
  styleUrls: ['./token.page.scss'],
})
export class TokenPage implements OnInit {
  generatedToken: string;
  thing: Thing;

  constructor(public modalController: ModalController, private thingsService: ThingsService) { }

  ngOnInit() {
  }

  dismiss() {
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  generate() {
    this.thingsService
      .getThingToken(this.thing.uuid)
      .subscribe(token => this.generatedToken = token);
  }
}
