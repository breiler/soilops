import {Injectable} from '@angular/core';
import {Observable, Observer, Subject} from "rxjs";
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private subject: Subject<MessageEvent>;
  private myWebSocket: WebSocketSubject<any>;

  public connect(url): Subject<MessageEvent> {
    if(!this.myWebSocket) {
      this.myWebSocket = webSocket(url);
      this.myWebSocket.next({message: 'some message'});

      this.subject = new Subject<MessageEvent>();
      this.myWebSocket.subscribe(dataFromServer => {
        this.subject.next(dataFromServer);
      });
    }

    return this.subject;
  }
}
