import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouteReuseStrategy} from '@angular/router';

import {IonicModule, IonicRouteStrategy} from '@ionic/angular';
import {SplashScreen} from '@ionic-native/splash-screen/ngx';
import {StatusBar} from '@ionic-native/status-bar/ngx';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';

import {IonicStorageModule} from '@ionic/storage';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MainPageModule} from "./pages/main/main.module";
import {LoginPageModule} from "./pages/login/login.module";
import {AuthGuardService} from "./service/auth-guard/auth-guard.service";
import {HttpInterceptorService} from "./service/http-interceptor/http-interceptor.service";
import {BindPageModule} from "./pages/bind/bind.module";
import {LogoutPageModule} from "./pages/logout/logout.module";
import {DevicePageModule} from "./pages/device/device.module";


@NgModule({
  declarations: [
    AppComponent
  ],
  entryComponents: [],
  imports: [BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    IonicStorageModule.forRoot(),
    MainPageModule,
    LoginPageModule,
    BindPageModule,
    LogoutPageModule,
    DevicePageModule
  ],
  providers: [
    StatusBar,
    SplashScreen,
    AuthGuardService,
    {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true}
  ],
  exports: [

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
