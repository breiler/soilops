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
import {InlineEditorComponent} from "./components/inline-editor/inline-editor.component";
import {HomePageModule} from "./pages/home/home.module";
import {MainPageModule} from "./pages/main/main.module";
import {LoginPageModule} from "./pages/login/login.module";
import {AuthGuardService} from "./service/auth-guard/auth-guard.service";
import {HttpInterceptorService} from "./service/http-interceptor/http-interceptor.service";
import {BindPageModule} from "./pages/bind/bind.module";
import {LogoutPage} from "./pages/logout/logout.page";
import {LogoutPageModule} from "./pages/logout/logout.module";

@NgModule({
  declarations: [AppComponent, InlineEditorComponent],
  entryComponents: [],
  imports: [BrowserModule, IonicModule.forRoot(), AppRoutingModule,
    HttpClientModule, IonicStorageModule.forRoot(),
    HomePageModule,
    MainPageModule,
    LoginPageModule,
    BindPageModule,
    LogoutPageModule
  ],
  providers: [
    StatusBar,
    SplashScreen,
    AuthGuardService,
    {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
