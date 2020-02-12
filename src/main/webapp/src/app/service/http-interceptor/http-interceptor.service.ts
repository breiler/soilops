import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor,HttpRequest,HttpResponse,HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import {catchError} from 'rxjs/operators';
import {Router} from "@angular/router";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
  constructor(private router : Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {

    return next.handle(req).pipe(
      catchError( (error) => {

        if (error instanceof HttpErrorResponse) {
          if (error.error instanceof ErrorEvent) {
            console.error("Error Event");
          } else {
            console.log(`error status : ${error.status} ${error.statusText}`);
            switch (error.status) {
              case 401:      //login
                //this.router.navigateByUrl("/login");
                break;
              case 403:     //forbidden
                //this.router.navigateByUrl("/unauthorized");
                break;
            }
          }
        } else {
          console.error("some thing else happened");
        }
        return throwError(error);


      })
    )
  }
}
