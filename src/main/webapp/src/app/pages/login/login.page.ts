import {Component, OnInit} from '@angular/core';
import {ModalController} from "@ionic/angular";
import {AuthService} from "../../service/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(public modalController: ModalController, public authService: AuthService) {
  }

  ngOnInit() {
  }

  dismiss() {
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  login(form: any) {
    this.authService.login(form.username, form.password)
      .subscribe(user => {
        this.dismiss();
      });
  }
}
