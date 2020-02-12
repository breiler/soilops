import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../service/auth/auth.service";
import { PlatformLocation } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  private token: string;
  private href: string;

  constructor(private route: ActivatedRoute, private authService: AuthService, private router: Router, private platformLocation: PlatformLocation) {
  }

  ngOnInit() {
    this.href = this.platformLocation.href.replace(this.platformLocation.pathname, '');
  }
}
